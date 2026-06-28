package com.example.laptoploan.features.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.EmptyState
import com.example.laptoploan.core.ui.components.NotificationCard
import com.example.laptoploan.core.ui.components.OrangeCircleButton
import com.example.laptoploan.core.ui.components.PrimaryButton
import com.example.laptoploan.core.ui.components.RoundedWhiteContainer
import com.example.laptoploan.core.ui.components.SchoolHeader
import com.example.laptoploan.core.ui.components.UserAvatar
import com.example.laptoploan.features.auth.viewmodel.AppUiState

@Composable
fun NotificationScreen(state: AppUiState, onBack: () -> Unit, onFinish: () -> Unit) {
    var done by remember { mutableStateOf(false) }
    val notification = state.notifications.firstOrNull()
    AppBackground {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth().padding(start = 20.dp, end = 24.dp, top = 48.dp), verticalAlignment = Alignment.CenterVertically) {
                OrangeCircleButton(onClick = onBack)
                Spacer(Modifier.weight(1f))
                Text(state.currentUser?.name ?: "Yoga Nugraha", color = Color.White, fontWeight = FontWeight.Bold)
                UserAvatar(Modifier.padding(start = 8.dp).height(28.dp))
            }
            SchoolHeader(Modifier.align(Alignment.CenterHorizontally), compact = true)
            Spacer(Modifier.height(22.dp))
            RoundedWhiteContainer(modifier = Modifier.weight(1f), topRadius = 34) {
                Column(Modifier.fillMaxSize()) {
                    if (state.activeBorrowing == null && notification == null) {
                        EmptyState("Tidak ada notifikasi", Modifier.weight(1f))
                    } else {
                        Spacer(Modifier.height(24.dp))
                        NotificationCard(
                            message = notification?.message ?: "Segera kumpulkan laptop atau kamu akan dikenakan sanksi!",
                            done = done,
                            onDoneChange = { done = it }
                        )
                        Spacer(Modifier.weight(1f))
                        PrimaryButton("Selesai", onClick = onFinish)
                        Spacer(Modifier.height(58.dp))
                    }
                }
            }
        }
    }
}
