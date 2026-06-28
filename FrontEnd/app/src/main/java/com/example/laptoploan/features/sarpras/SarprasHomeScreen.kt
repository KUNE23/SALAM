package com.example.laptoploan.features.sarpras

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laptoploan.core.data.model.LaptopStatus
import com.example.laptoploan.core.ui.components.LaptopListItem
import com.example.laptoploan.core.ui.theme.PrimaryBlue
import com.example.laptoploan.features.admin.BorrowingRow
import com.example.laptoploan.features.admin.DashboardScaffold
import com.example.laptoploan.features.auth.viewmodel.AppUiState
import com.example.laptoploan.features.auth.viewmodel.AppViewModel

@Composable
fun SarprasHomeScreen(state: AppUiState, viewModel: AppViewModel, onLogout: () -> Unit) {
    DashboardScaffold(title = "Sarpras Dashboard", onLogout = onLogout) {
        Text("Laptop Tersedia", color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 20.sp)
        Spacer(Modifier.height(10.dp))
        state.laptops.filter { it.status == LaptopStatus.AVAILABLE }.forEach {
            LaptopListItem(it)
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(18.dp))
        Text("Peminjam Aktif", color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 20.sp)
        Spacer(Modifier.height(10.dp))
        state.activeBorrowings.forEach { borrowing ->
            BorrowingRow("User #${borrowing.userId} perlu verifikasi Laptop #${borrowing.laptopId}", onReturn = { viewModel.returnBorrowing(borrowing) })
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(18.dp))
        Text("Notifikasi Keterlambatan", color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))
        Text("Belum ada keterlambatan aktif.", color = PrimaryBlue)
    }
}
