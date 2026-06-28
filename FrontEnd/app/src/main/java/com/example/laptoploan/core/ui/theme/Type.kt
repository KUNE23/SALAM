package com.example.laptoploan.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight

val AppTypography = Typography().let {
    it.copy(
        headlineLarge = it.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
        headlineMedium = it.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
        titleLarge = it.titleLarge.copy(fontWeight = FontWeight.Bold),
        titleMedium = it.titleMedium.copy(fontWeight = FontWeight.Bold),
        bodyLarge = it.bodyLarge.copy(fontWeight = FontWeight.Medium)
    )
}
