package com.example.bioformulationmedicines.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1976D2),
    secondary = Color(0xFF64B5F6),
    background = Color(0xFF121212)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),
    secondary = Color(0xFF64B5F6),
    background = Color(0xFFFFFFFF)
)

@Composable
fun BioFormulationTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(colorScheme = colors, content = content)
}