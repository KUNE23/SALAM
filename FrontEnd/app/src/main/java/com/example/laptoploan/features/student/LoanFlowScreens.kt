package com.example.laptoploan.features.student

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.CheckBadge
import com.example.laptoploan.core.ui.components.OrangeCircleButton
import com.example.laptoploan.core.ui.components.SchoolHeader
import com.example.laptoploan.core.ui.theme.OrangeAccent
import com.example.laptoploan.core.ui.theme.PrimaryBlue

@Composable
fun TermsScreen(onBack: () -> Unit, onCancel: () -> Unit, onAgree: () -> Unit) {
    var checked by remember { mutableStateOf(false) }
    BluePanelScreen(onBack = onBack) {
        Text("Syarat dan ketentuan", color = Color.White, fontWeight = FontWeight.Black, fontSize = 21.sp)
        Spacer(Modifier.height(14.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                modifier = Modifier.size(36.dp),
                colors = CheckboxDefaults.colors(checkedColor = Color.Black, uncheckedColor = Color.White, checkmarkColor = Color.White)
            )
            Text(
                "Dengan ini saya menyetujui syarat dan\nketentuan",
                color = Color.White,
                fontSize = 11.sp,
                lineHeight = 13.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        Spacer(Modifier.height(72.dp))
        ActionButtons(
            onOk = { if (checked) onAgree() else checked = true },
            onCancel = onCancel
        )
    }
}

@Composable
fun VerificationScreen(onBack: () -> Unit, onCancel: () -> Unit, onOk: () -> Unit) {
    BluePanelScreen(onBack = onBack) {
        Text("Mohon tunggu admin\nmemverifikasi", color = Color.White, fontWeight = FontWeight.Black, fontSize = 21.sp, lineHeight = 26.sp)
        Spacer(Modifier.height(8.dp))
        Text("00:00", color = Color.White, fontSize = 16.sp)
        Spacer(Modifier.height(92.dp))
        ActionButtons(onOk = onOk, onCancel = onCancel)
    }
}

@Composable
fun LoanSuccessScreen(onNext: () -> Unit) {
    AppBackground {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(84.dp))
            SchoolHeader()
            Spacer(Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
                    .background(Color.White)
            ) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(68.dp))
                    CheckBadge()
                    Spacer(Modifier.weight(1f))
                    Box(Modifier.fillMaxWidth().height(310.dp)) {
                        Canvas(Modifier.fillMaxSize()) {
                            drawRoundRect(PrimaryBlue, cornerRadius = androidx.compose.ui.geometry.CornerRadius(36f, 36f))
                        }
                        Column(
                            Modifier.fillMaxSize().padding(horizontal = 32.dp, vertical = 54.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Pinjaman berhasil !", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                                Spacer(Modifier.height(10.dp))
                                Text("Jangan lupa di kembalikan lagi laptopnya ke\nkantor  paling lambat jam 16.00 !", color = Color.White, fontSize = 16.sp, lineHeight = 19.sp)
                            }
                            Button(
                                onClick = onNext,
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = PrimaryBlue),
                                shape = RoundedCornerShape(30.dp)
                            ) {
                                Text("Lanjut", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BluePanelScreen(onBack: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    AppBackground {
        Column(Modifier.fillMaxSize()) {
            OrangeCircleButton(onClick = onBack, modifier = Modifier.padding(start = 20.dp, top = 48.dp))
            Spacer(Modifier.weight(1f))
            SchoolHeader(Modifier.align(Alignment.CenterHorizontally), compact = true)
            Spacer(Modifier.height(30.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(378.dp)
                    .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                    .background(PrimaryBlue)
                    .border(2.dp, OrangeAccent, RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                    .padding(horizontal = 36.dp, vertical = 82.dp)
            ) {
                Column(Modifier.fillMaxSize(), content = content)
            }
        }
    }
}

@Composable
private fun ActionButtons(onOk: () -> Unit, onCancel: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        WhitePillButton("Ok", onClick = onOk, modifier = Modifier.weight(1f))
        WhitePillButton("Batal", onClick = onCancel, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun WhitePillButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}
