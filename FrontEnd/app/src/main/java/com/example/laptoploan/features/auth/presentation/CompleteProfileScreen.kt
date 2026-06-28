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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.laptoploan.core.data.model.User
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.AppTextField
import com.example.laptoploan.core.ui.components.OrangeCircleButton
import com.example.laptoploan.core.ui.components.PrimaryButton
import com.example.laptoploan.core.ui.components.RoundedWhiteContainer
import com.example.laptoploan.core.ui.components.SchoolHeader
import com.example.laptoploan.core.ui.components.UserAvatar

@Composable
fun CompleteProfileScreen(
    user: User?,
    onBack: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var nisn by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var jurusan by remember { mutableStateOf("") }

    AppBackground {
        Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState())) {
            OrangeCircleButton(onClick = onBack, modifier = Modifier.padding(start = 20.dp, top = 48.dp))
            Spacer(Modifier.height(72.dp))
            SchoolHeader(Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(28.dp))
            RoundedWhiteContainer(modifier = Modifier.weight(1f, fill = false), topRadius = 34) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    UserAvatar(large = true)
                    Spacer(Modifier.height(24.dp))
                    AppTextField(user?.name.orEmpty(), {}, "Nama", readOnly = true)
                    Spacer(Modifier.height(18.dp))
                    AppTextField(kelas, { kelas = it }, "Kelas")
                    Spacer(Modifier.height(18.dp))
                    AppTextField(jurusan, { jurusan = it }, "Jurusan")
                    Spacer(Modifier.height(18.dp))
                    AppTextField(nisn, { nisn = it }, "NISN")
                    Spacer(Modifier.height(24.dp))
                    PrimaryButton("Simpan", onClick = { onSave(nisn, kelas, jurusan) })
                    Spacer(Modifier.height(90.dp))
                }
            }
        }
    }
}
