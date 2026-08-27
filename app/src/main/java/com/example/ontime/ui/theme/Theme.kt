package com.example.ontime.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Приложение всегда в тёмной теме (как iOS-«Часы» / «Напоминания» на скриншотах).
private val OnTimeColorScheme = darkColorScheme(
    primary = AccentOrange,
    onPrimary = Ink,
    secondary = AccentBlue,
    onSecondary = TextPrimary,
    tertiary = AccentGreen,
    background = Ink,
    onBackground = TextPrimary,
    surface = Surface1,
    onSurface = TextPrimary,
    surfaceVariant = Surface2,
    onSurfaceVariant = TextSecondary,
    outline = Separator,
    outlineVariant = Separator,
    error = AccentRed,
)

@Composable
fun OnTimeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = OnTimeColorScheme,
        typography = Typography,
        content = content
    )
}
