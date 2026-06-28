package com.example.laptoploan.features.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laptoploan.core.ui.components.AppBackground
import com.example.laptoploan.core.ui.components.CheckBadge
import com.example.laptoploan.core.ui.components.PrimaryButton
import com.example.laptoploan.core.ui.components.RoundedWhiteContainer
import com.example.laptoploan.core.ui.components.SchoolHeader

@Composable
fun SuccessScreen(title: String, subtitle: String, onNext: () -> Unit) {
    AppBackground {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(84.dp))
            SchoolHeader()
            Spacer(Modifier.height(50.dp))
            RoundedWhiteContainer(
                modifier = Modifier.weight(1f),
                topRadius = 34,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(68.dp))
                    CheckBadge()
                    Spacer(Modifier.weight(1f))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(310.dp)
                            .padding(top = 2.dp)
                    ) {
                        androidx.compose.foundation.Canvas(Modifier.fillMaxSize()) {
                            drawRoundRect(Color(0xFF1DA1DB), cornerRadius = androidx.compose.ui.geometry.CornerRadius(36f, 36f))
                        }
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 32.dp, vertical = 54.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(title, color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                                Spacer(Modifier.height(10.dp))
                                Text(subtitle, color = Color.White, fontSize = 16.sp)
                            }
                            PrimaryButton("Lanjut", onNext, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}
