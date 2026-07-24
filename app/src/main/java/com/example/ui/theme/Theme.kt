package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = GoldAccent,
    onPrimary = Color.Black,
    secondary = PurpleAccent,
    onSecondary = Color.White,
    tertiary = CyanAccent,
    background = BgDark0,
    surface = BgDark1,
    surfaceVariant = BgDark2,
    onBackground = Color(0xFFE8E6F3),
    onSurface = Color(0xFFE8E6F3),
    error = RedDanger
)

private val LightColorScheme = lightColorScheme(
    primary = GoldAccent,
    onPrimary = Color.White,
    secondary = PurpleAccent,
    onSecondary = Color.White,
    tertiary = CyanAccent,
    background = BgLight0,
    surface = BgLight1,
    surfaceVariant = BgLight2,
    onBackground = Color(0xFF1A1A2E),
    onSurface = Color(0xFF1A1A2E),
    error = RedDanger
)

@Composable
fun ArcanumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
