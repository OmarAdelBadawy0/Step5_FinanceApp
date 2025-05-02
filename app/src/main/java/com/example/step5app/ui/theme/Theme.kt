package com.example.step5app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    surface = Light_white_gray,
    primary = Purple_blue,
    onPrimary = White,
    surfaceVariant = Light_gray,
    onSurface = White,
    onSurfaceVariant = Black,
    surfaceContainer = Dark_gray,    // fields on the surface
    background = Light_black
)

private val LightColorScheme = lightColorScheme(

    surfaceVariant = Light_gray,    // secondary card
    primary = Light_purple,         // Buttons
    onPrimary = White,              // onButtons
    surface = White_gray,           // main card
    onSurface = Black,              // onMainCard
    onSurfaceVariant = Black,       // onSecondaryCard
    surfaceContainer = White,


)

@Composable
fun Step5AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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