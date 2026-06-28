package com.example.laptoploan.features.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.AppTextField
import com.example.laptoploan.core.ui.components.OrangeCircleButton
import com.example.laptoploan.core.ui.components.PrimaryButton
import com.example.laptoploan.core.ui.components.RoundedWhiteContainer
import com.example.laptoploan.core.ui.components.SchoolHeader
import com.example.laptoploan.core.ui.theme.PrimaryBlue

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onRegister: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    AppBackground {
        Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState())) {
            OrangeCircleButton(onClick = onBack, modifier = Modifier.padding(start = 20.dp, top = 48.dp))
            Spacer(Modifier.height(76.dp))
            SchoolHeader(Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(28.dp))
            RoundedWhiteContainer(modifier = Modifier.weight(1f, fill = false), topRadius = 34) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Buat Akun", color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.height(26.dp))
                    AppTextField(name, { name = it }, "Nama")
                    Spacer(Modifier.height(18.dp))
                    AppTextField(email, { email = it }, "E-mail atau no. telepon")
                    Spacer(Modifier.height(18.dp))
                    AppTextField(password, { password = it }, "Kata sandi", password = true, passwordVisible = visible, onTogglePassword = { visible = !visible })
                    Spacer(Modifier.height(22.dp))
                    PrimaryButton("Lanjut", onClick = { onRegister(name, email, password) })
                    Spacer(Modifier.height(160.dp))
                }
            }
        }
    }
}
