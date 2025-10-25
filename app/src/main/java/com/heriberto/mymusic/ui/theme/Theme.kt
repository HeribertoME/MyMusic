package com.heriberto.mymusic.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    secondary = SkyBlue,
    onSecondary = Navy,
    tertiary = Navy,
    onTertiary = Color.White,
    background = White,
    onBackground = Ink,
    surface = White,
    onSurface = Ink,
    surfaceVariant = Color(0xFFEFF7FA),
    onSurfaceVariant = Navy,
    outline = Color(0xFFBFDCEC)
)

private val LightColorScheme = lightColorScheme(
    primary = Teal,
    onPrimary = Ink,
    secondary = SkyBlue,
    onSecondary = Ink,
    tertiary = Navy,
    onTertiary = Color.White,
    background = Ink,
    onBackground = White,
    surface = Color(0xFF1F2B3E),
    onSurface = White,
    surfaceVariant = Color(0xFF24344C),
    onSurfaceVariant = Color(0xFFCCE7F6),
    outline = Color(0xFF476089)
)

@Composable
fun MyMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}