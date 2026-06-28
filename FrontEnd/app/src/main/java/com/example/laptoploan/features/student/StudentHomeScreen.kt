package com.example.laptoploan.features.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.OrangeCircleButton
import com.example.laptoploan.core.ui.components.PrimaryButton
import com.example.laptoploan.core.ui.components.RoundedWhiteContainer
import com.example.laptoploan.core.ui.components.SchoolHeader
import com.example.laptoploan.core.ui.components.StatsCard
import com.example.laptoploan.core.ui.components.UserAvatar
import com.example.laptoploan.core.ui.theme.OrangeAccent
import com.example.laptoploan.core.ui.theme.PrimaryBlue
import com.example.laptoploan.features.auth.viewmodel.AppUiState

@Composable
fun StudentHomeScreen(
    state: AppUiState,
    onLaptop: () -> Unit,
    onNotification: () -> Unit,
    onProfile: () -> Unit,
    onLogout: () -> Unit
) {
    val user = state.currentUser
    val hasBorrowing = state.activeBorrowing != null
    AppBackground {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(start = 20.dp, end = 24.dp, top = 48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OrangeCircleButton(text = if (hasBorrowing) "H" else "O", onClick = onLogout)
                Spacer(Modifier.weight(1f))
                Text(user?.name ?: "Yoga Nugraha", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.padding(4.dp))
                UserAvatar(Modifier.padding(start = 8.dp).height(28.dp), large = false)
            }
            SchoolHeader(Modifier.align(Alignment.CenterHorizontally), compact = true)
            Spacer(Modifier.height(34.dp))
            Row(Modifier.fillMaxWidth().padding(horizontal = 56.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                StatsCard("Meminjam", state.userBorrowedCount.toString())
                StatsCard("Tersedia", state.availableCount.toString())
                StatsCard("Notifikasi", if (state.unreadNotificationCount > 0) state.unreadNotificationCount.toString() else "!", alert = state.unreadNotificationCount > 0, onClick = onNotification)
            }
            Spacer(Modifier.height(24.dp))
            RoundedWhiteContainer(modifier = Modifier.weight(1f), topRadius = 34) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(if (hasBorrowing) 6.dp else 34.dp))
                    Text("Hai ${user?.name?.substringBefore(' ') ?: "Yoga"}!", color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 32.sp)
                    Spacer(Modifier.height(8.dp))
                    if (hasBorrowing) {
                        androidx.compose.foundation.layout.Box(
                            Modifier
                                .fillMaxWidth(0.58f)
                                .height(50.dp)
                                .padding(top = 0.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.Canvas(Modifier.fillMaxSize()) {
                                drawRoundRect(OrangeAccent, cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f, 18f))
                            }
                            Text("Kamu Pinjam 1 Laptop", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Spacer(Modifier.height(24.dp))
                        Text(
                            "Jangan lupa di kembalikan lagi laptopnya ke kantor paling lambat jam 16.00 !",
                            color = PrimaryBlue,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp
                        )
                    } else {
                        Text("Yah kamu belum pinjam Laptop satupun", color = PrimaryBlue, fontSize = 16.sp, textAlign = TextAlign.Center)
                    }
                    Spacer(Modifier.weight(1f))
                    PrimaryButton("Lihat Daftar Laptop", onLaptop)
                    Spacer(Modifier.height(40.dp))
                }
            }
        }
    }
}
