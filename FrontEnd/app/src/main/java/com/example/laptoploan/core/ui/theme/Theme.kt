package com.example.laptoploan.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = OrangeAccent,
    background = PrimaryBlue,
    surface = WhiteCard,
    onPrimary = WhiteCard,
    onSurface = DarkBlueText
)

@Composable
fun LaptopLoanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightScheme,
        typography = AppTypography,
        content = content
    )
}
