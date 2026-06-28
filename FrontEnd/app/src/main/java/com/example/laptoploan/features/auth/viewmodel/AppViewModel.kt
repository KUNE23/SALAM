package com.example.laptoploan.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.laptoploan.core.data.model.Borrowing
import com.example.laptoploan.core.data.model.Laptop
import com.example.laptoploan.core.data.model.LaptopStatus
import com.example.laptoploan.core.data.model.Notification
import com.example.laptoploan.core.data.model.User
import com.example.laptoploan.core.data.model.UserRole
import com.example.laptoploan.core.data.repository.LaptopLoanRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AppUiState(
    val currentUser: User? = null,
    val laptops: List<Laptop> = emptyList(),
    val activeBorrowing: Borrowing? = null,
    val activeBorrowings: List<Borrowing> = emptyList(),
    val notifications: List<Notification> = emptyList(),
    val studentCount: Int = 0,
    val error: String? = null,
    val loading: Boolean = false
) {
    val borrowedCount: Int get() = laptops.count { it.status == LaptopStatus.BORROWED }
    val availableCount: Int get() = laptops.count { it.status == LaptopStatus.AVAILABLE }
    val userBorrowedCount: Int get() = if (activeBorrowing == null) 0 else 1
    val unreadNotificationCount: Int get() = notifications.count { !it.isRead }
}

private data class DataState(
    val currentUser: User?,
    val laptops: List<Laptop>,
    val activeBorrowing: Borrowing?,
    val activeBorrowings: List<Borrowing>,
    val notifications: List<Notification>,
    val students: List<User>
)

private data class MainDataState(
    val currentUser: User?,
    val laptops: List<Laptop>,
    val activeBorrowing: Borrowing?,
    val activeBorrowings: List<Borrowing>,
    val notifications: List<Notification>
)

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModel(private val repository: LaptopLoanRepository) : ViewModel() {
    private val error = MutableStateFlow<String?>(null)
    private val loading = MutableStateFlow(false)

    init {
        viewModelScope.launch { runCatching { repository.refreshHome() } }
    }

    val uiState: StateFlow<AppUiState> = repository.sessionUserId
        .flatMapLatest { userId ->
            val userFlow = userId?.let { repository.observeUser(it) } ?: flowOf(null)
            val activeFlow = userId?.let { repository.observeActiveBorrowing(it) } ?: flowOf(null)
            val notificationFlow = userId?.let { repository.observeNotifications(it) } ?: flowOf(emptyList<Notification>())
            val mainDataFlow = combine(userFlow, repository.laptops, activeFlow, repository.activeBorrowings, notificationFlow) {
                    user, laptops, activeBorrowing, activeBorrowings, notifications ->
                MainDataState(user, laptops, activeBorrowing, activeBorrowings, notifications)
            }
            val dataFlow = combine(mainDataFlow, repository.students) { main, students ->
                DataState(main.currentUser, main.laptops, main.activeBorrowing, main.activeBorrowings, main.notifications, students)
            }
            combine(dataFlow, error, loading) { data, errorMessage, isLoading ->
                AppUiState(
                    currentUser = data.currentUser,
                    laptops = data.laptops,
                    activeBorrowing = data.activeBorrowing,
                    activeBorrowings = data.activeBorrowings,
                    notifications = data.notifications,
                    studentCount = data.students.size,
                    error = errorMessage,
                    loading = isLoading
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppUiState())

    fun clearError() {
        error.value = null
    }

    fun register(name: String, email: String, password: String, role: UserRole, onSuccess: () -> Unit) {
        if (name.isBlank()) return setError("Nama tidak boleh kosong")
        if (email.isBlank()) return setError("Email atau nomor telepon tidak boleh kosong")
        if (password.length < 6) return setError("Password minimal 6 karakter")
        viewModelScope.launch {
            loading.value = true
            runCatching { repository.register(name.trim(), email.trim(), password, role) }
                .onSuccess { onSuccess() }
                .onFailure { setError(it.message ?: "Registrasi gagal") }
            loading.value = false
        }
    }

    fun login(email: String, password: String, role: UserRole, onSuccess: (UserRole) -> Unit) {
        if (email.isBlank() || password.isBlank()) return setError("Email dan password wajib diisi")
        viewModelScope.launch {
            loading.value = true
            val user = runCatching { repository.login(email.trim(), password, role) }
                .onFailure { setError(it.message ?: "Login gagal") }
                .getOrNull()
            loading.value = false
            if (user == null) setError("Akun tidak ditemukan")
            else onSuccess(user.role)
        }
    }

    fun completeProfile(nisn: String, className: String, major: String, onSuccess: () -> Unit) {
        val user = uiState.value.currentUser ?: return setError("Session tidak ditemukan")
        if (nisn.isBlank() || className.isBlank() || major.isBlank()) return setError("Lengkapi semua data")
        viewModelScope.launch {
            loading.value = true
            runCatching { repository.completeProfile(user, nisn.trim(), "${className.trim()} ${major.trim()}") }
                .onSuccess { onSuccess() }
                .onFailure { setError(it.message ?: "Gagal menyimpan profil") }
            loading.value = false
        }
    }

    fun borrowLaptop(code: String, onSuccess: () -> Unit) {
        val user = uiState.value.currentUser ?: return setError("Session tidak ditemukan")
        if (uiState.value.activeBorrowing != null) return setError("Kamu masih memiliki pinjaman aktif")
        if (code.isBlank()) return setError("Kode laptop wajib diisi")
        viewModelScope.launch {
            loading.value = true
            val result = repository.borrowLaptop(user.id, code)
            result.fold(
                onSuccess = { onSuccess() },
                onFailure = { setError(it.message ?: "Gagal meminjam laptop") }
            )
            loading.value = false
        }
    }

    fun returnActiveBorrowing(onSuccess: () -> Unit) {
        val borrowing = uiState.value.activeBorrowing ?: return setError("Tidak ada pinjaman aktif")
        viewModelScope.launch {
            loading.value = true
            runCatching { repository.returnBorrowing(borrowing) }
                .onSuccess { onSuccess() }
                .onFailure { setError(it.message ?: "Gagal mengembalikan laptop") }
            loading.value = false
        }
    }

    fun returnBorrowing(borrowing: Borrowing) {
        viewModelScope.launch { runCatching { repository.returnBorrowing(borrowing) }.onFailure { setError(it.message ?: "Gagal mengembalikan laptop") } }
    }

    fun addLaptop(name: String, code: String) {
        if (name.isBlank() || code.isBlank()) return setError("Nama dan kode laptop wajib diisi")
        viewModelScope.launch { repository.addLaptop(name.trim(), code.trim()) }
    }

    fun deleteLaptop(laptop: Laptop) {
        viewModelScope.launch { repository.deleteLaptop(laptop) }
    }

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            onDone()
        }
    }

    private fun setError(message: String) {
        error.value = message
    }
}

class AppViewModelFactory(private val repository: LaptopLoanRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(repository) as T
    }
}
