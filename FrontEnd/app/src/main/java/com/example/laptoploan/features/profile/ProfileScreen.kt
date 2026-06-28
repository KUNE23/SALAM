package com.example.laptoploan.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laptoploan.core.data.model.User
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.AppTextField
import com.example.laptoploan.core.ui.components.OrangeCircleButton
import com.example.laptoploan.core.ui.components.PrimaryButton
import com.example.laptoploan.core.ui.components.RoundedWhiteContainer
import com.example.laptoploan.core.ui.components.SchoolHeader
import com.example.laptoploan.core.ui.components.UserAvatar
import com.example.laptoploan.core.ui.theme.PrimaryBlue

@Composable
fun ProfileScreen(user: User?, onBack: () -> Unit, onLogout: () -> Unit) {
    AppBackground {
        Column(Modifier.fillMaxSize()) {
            OrangeCircleButton(text = "H", onClick = onBack, modifier = Modifier.padding(start = 20.dp, top = 48.dp))
            Spacer(Modifier.height(132.dp))
            SchoolHeader(Modifier.align(Alignment.CenterHorizontally), compact = true)
            Spacer(Modifier.height(22.dp))
            RoundedWhiteContainer(modifier = Modifier.weight(1f), topRadius = 34) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Profil Anda", color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 32.sp)
                    Spacer(Modifier.height(48.dp))
                    UserAvatar(large = true)
                    Spacer(Modifier.height(24.dp))
                    Text(user?.name ?: "-", color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 22.sp)
                    Spacer(Modifier.height(24.dp))
                    AppTextField(user?.nisn.orEmpty(), {}, "NISN", readOnly = true)
                    Spacer(Modifier.height(18.dp))
                    AppTextField(user?.className.orEmpty(), {}, "Kelas", readOnly = true)
                    Spacer(Modifier.height(52.dp))
                    PrimaryButton("Keluar", onClick = onLogout)
                }
            }
        }
    }
}
