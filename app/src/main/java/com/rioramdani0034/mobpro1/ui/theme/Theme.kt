package com.rioramdani0034.mobpro1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.rioramdani0034.mobpro1.util.SettingsDataStore

@Composable
fun Mobpro1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)
    val themeColor by dataStore.themeColorFlow.collectAsState(initial = SettingsDataStore.COLOR_DEFAULT)

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val dynamicColors = if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }

            // Apply custom color overrides based on the selected theme
            if (themeColor != SettingsDataStore.COLOR_DEFAULT) {
                val themeColors = getThemeColors(themeColor)
                dynamicColors.copy(
                    primary = themeColors.primary,
                    primaryContainer = themeColors.primaryContainer,
                    secondaryContainer = themeColors.secondaryContainer,
                    tertiaryContainer = themeColors.tertiaryContainer,
                    errorContainer = themeColors.errorContainer
                )
            } else {
                dynamicColors
            }
        }
        else -> getThemeColors(themeColor)
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}