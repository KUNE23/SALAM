package com.example.laptoploan.features.laptop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.laptoploan.core.data.model.LaptopStatus
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.AppTextField
import com.example.laptoploan.core.ui.components.LaptopListItem
import com.example.laptoploan.core.ui.components.OrangeCircleButton
import com.example.laptoploan.core.ui.components.PrimaryButton
import com.example.laptoploan.core.ui.components.RoundedWhiteContainer
import com.example.laptoploan.core.ui.components.SchoolHeader
import com.example.laptoploan.core.ui.theme.PrimaryBlue
import com.example.laptoploan.features.auth.viewmodel.AppUiState

@Composable
fun LaptopListScreen(state: AppUiState, onBack: () -> Unit, onBorrow: (String) -> Unit) {
    var code by remember { mutableStateOf("") }
    val available = state.laptops.filter { it.status == LaptopStatus.AVAILABLE }

    AppBackground {
        Column(Modifier.fillMaxSize().imePadding()) {
            OrangeCircleButton(onClick = onBack, modifier = Modifier.padding(start = 20.dp, top = 48.dp))
            Spacer(Modifier.height(38.dp))
            SchoolHeader(Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(172.dp))
            RoundedWhiteContainer(modifier = Modifier.weight(1f), topRadius = 34) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Daftar Laptop", color = PrimaryBlue, fontWeight = FontWeight.Black, fontSize = 32.sp)
                    Spacer(Modifier.height(36.dp))
                    Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())) {
                        available.forEach {
                            LaptopListItem(it)
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                        AppTextField(code, { code = it }, "Kode Laptop", modifier = Modifier.weight(1f), gray = true)
                        Spacer(Modifier.width(12.dp))
                        Text("[A]", fontSize = 34.sp, fontWeight = FontWeight.Black)
                    }
                    Spacer(Modifier.height(28.dp))
                    PrimaryButton("Lanjut", onClick = { onBorrow(code) })
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}
