package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CyberMint,
    secondary = AtlasBlue,
    tertiary = MarrakeshSunset,
    background = CarbonBg,
    surface = SurfaceSlate,
    onPrimary = Color(0xFF05070B),
    onSecondary = Color(0xFF05070B),
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceSlateAlt,
    onSurfaceVariant = TextSecondary,
    outline = GridBorder
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00B050),
    secondary = Color(0xFF0078D4),
    tertiary = Color(0xFFE84E1B),
    background = Color(0xFFF7F8FA),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF111827),
    onSurface = Color(0xFF111827),
    surfaceVariant = Color(0xFFEBF0FA),
    onSurfaceVariant = Color(0xFF4B5563),
    outline = Color(0xFFD1D5DB)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force Dark Theme for consistent premium branding
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
