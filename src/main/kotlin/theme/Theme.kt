package theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

enum class ThemeMode {
    Dark,
    Light,
    Black
}

@Composable
fun PalmApiTheme(
    currentTheme: String,
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {

    val themeMode = when (currentTheme) {
        ThemeMode.Black.name -> ThemeMode.Black
        ThemeMode.Dark.name -> ThemeMode.Dark
        ThemeMode.Light.name -> ThemeMode.Light
        else -> ThemeMode.Light
    }
    val colorScheme = when (themeMode) {
        ThemeMode.Light -> LightColors
        ThemeMode.Dark -> DarkColors
        ThemeMode.Black -> BlackColors
    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}