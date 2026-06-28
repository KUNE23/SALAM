package com.example.laptoploan.core.data.repository

import com.example.laptoploan.core.data.model.Borrowing
import com.example.laptoploan.core.data.model.BorrowingStatus
import com.example.laptoploan.core.data.model.Laptop
import com.example.laptoploan.core.data.model.Notification
import com.example.laptoploan.core.data.model.User
import com.example.laptoploan.core.data.model.UserRole
import com.example.laptoploan.core.network.ApiResponse
import com.example.laptoploan.core.network.ApiService
import com.example.laptoploan.core.session.SessionManager
import com.example.laptoploan.core.utils.ErrorMapper
import com.example.laptoploan.data.remote.dto.CreateLoanRequest
import com.example.laptoploan.data.remote.dto.LoginRequest
import com.example.laptoploan.data.remote.dto.RefreshRequest
import com.example.laptoploan.data.remote.dto.RegisterRequest
import com.example.laptoploan.data.remote.dto.UpdateProfileRequest
import com.example.laptoploan.data.remote.mapper.stableLongId
import com.example.laptoploan.data.remote.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response

class LaptopLoanRepository(
    private val api: ApiService,
    private val sessionManager: SessionManager
) {
    val sessionUserId = sessionManager.userId
    private val currentUser = MutableStateFlow<User?>(null)
    private val laptopState = MutableStateFlow<List<Laptop>>(emptyList())
    private val activeBorrowingState = MutableStateFlow<Borrowing?>(null)
    private val activeBorrowingsState = MutableStateFlow<List<Borrowing>>(emptyList())
    private val notificationState = MutableStateFlow<List<Notification>>(emptyList())
    private val studentState = MutableStateFlow<List<User>>(emptyList())
    private val backendLoanIds = mutableMapOf<Long, String>()

    val laptops: Flow<List<Laptop>> = laptopState
    val activeBorrowings: Flow<List<Borrowing>> = activeBorrowingsState
    val students: Flow<List<User>> = studentState

    fun observeUser(id: Long): Flow<User?> = currentUser
    fun observeAvailableLaptops(): Flow<List<Laptop>> = laptopState
    fun observeActiveBorrowing(userId: Long): Flow<Borrowing?> = activeBorrowingState
    fun observeNotifications(userId: Long): Flow<List<Notification>> = notificationState

    suspend fun register(name: String, emailOrPhone: String, password: String, role: UserRole): Long {
        requireEmail(emailOrPhone)
        val data = request { api.register(RegisterRequest(name, emailOrPhone, password)) }
        val user = data.user.toModel()
        sessionManager.saveSession(data.accessToken, data.refreshToken, user.id)
        currentUser.value = user
        refreshHome()
        return user.id
    }

    suspend fun login(emailOrPhone: String, password: String, role: UserRole): User? {
        requireEmail(emailOrPhone)
        val data = request { api.login(LoginRequest(emailOrPhone, password)) }
        val user = data.user.toModel()
        sessionManager.saveSession(data.accessToken, data.refreshToken, user.id)
        currentUser.value = user
        refreshHome()
        return user
    }

    suspend fun completeProfile(user: User, nisn: String, className: String) {
        val parts = className.trim().split(" ", limit = 2)
        val updated = request {
            api.updateProfile(
                UpdateProfileRequest(
                    name = user.name,
                    nisn = nisn,
                    className = parts.firstOrNull().orEmpty(),
                    major = parts.getOrNull(1)
                )
            )
        }
        currentUser.value = updated.toModel()
    }

    suspend fun refreshHome() {
        runCatching { currentUser.value = request { api.profile() }.toModel() }
        runCatching { laptopState.value = request { api.laptops() }.map { it.toModel() } }
        runCatching {
            val userId = currentUser.value?.id ?: 0L
            val loans = request { api.myLoans() }
            activeBorrowingsState.value = loans.map { loan ->
                val model = loan.toModel(userId)
                backendLoanIds[model.id] = loan.id
                model
            }
            activeBorrowingState.value = activeBorrowingsState.value.firstOrNull {
                it.status == BorrowingStatus.BORROWED
            }
        }
        runCatching { notificationState.value = request { api.notifications() }.map { it.toModel() } }
    }

    suspend fun borrowLaptop(userId: Long, code: String): Result<Unit> {
        return runCatching {
            request { api.createLoan(CreateLoanRequest(code.trim().uppercase())) }
            refreshHome()
        }
    }

    suspend fun returnBorrowing(borrowing: Borrowing) {
        val backendId = backendLoanIds[borrowing.id] ?: throw IllegalStateException("Data pinjaman tidak ditemukan")
        request { api.returnLoan(backendId) }
        refreshHome()
    }

    suspend fun addLaptop(name: String, code: String) = Unit
    suspend fun updateLaptop(laptop: Laptop) = Unit
    suspend fun deleteLaptop(laptop: Laptop) = Unit

    suspend fun logout() {
        runCatching { api.logout() }
        sessionManager.clear()
        currentUser.value = null
        laptopState.value = emptyList()
        activeBorrowingState.value = null
        activeBorrowingsState.value = emptyList()
        notificationState.value = emptyList()
    }

    private fun requireEmail(value: String) {
        if (!value.contains("@")) throw IllegalArgumentException("Backend membutuhkan email yang valid")
    }

    private suspend fun <T> request(call: suspend () -> Response<ApiResponse<T>>): T {
        val response = try {
            call()
        } catch (error: Throwable) {
            throw IllegalStateException(ErrorMapper.fromThrowable(error))
        }
        if (response.code() == 401 && tryRefresh()) {
            val retry = call()
            return unwrap(retry)
        }
        return unwrap(response)
    }

    private fun <T> unwrap(response: Response<ApiResponse<T>>): T {
        val body = response.body()
        if (response.isSuccessful && body?.success == true && body.data != null) return body.data
        throw IllegalStateException(ErrorMapper.fromCode(response.code(), body?.message))
    }

    private suspend fun tryRefresh(): Boolean {
        val token = sessionManager.getRefreshToken() ?: return false
        return runCatching {
            val body = api.refresh(RefreshRequest(token)).body()
            val data = body?.data ?: return false
            sessionManager.updateAccessToken(data.accessToken)
            if (data.refreshToken != null) {
                val userId = currentUser.value?.id ?: sessionManager.userIdHash()
                sessionManager.saveSession(data.accessToken, data.refreshToken, userId)
            }
            true
        }.getOrDefault(false)
    }

    private suspend fun SessionManager.userIdHash(): Long = getAccessToken().orEmpty().stableLongId()
}
