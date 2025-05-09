package com.rioramdani0034.mobpro1.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.rioramdani0034.mobpro1.util.SettingsDataStore

// Default theme colors
val md_theme_light_primary = Color(0xFF006C51)
val md_theme_light_primaryContainer = Color(0xFF89F4D3)
val md_theme_light_secondaryContainer = Color(0xFFD0E6F2)
val md_theme_light_tertiaryContainer = Color(0xFFE8DEF8)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)

// Red theme
val red_primary = Color(0xFFB71C1C)
val red_primaryContainer = Color(0xFFFFCDD2)
val red_secondaryContainer = Color(0xFFFFEBEE)
val red_tertiaryContainer = Color(0xFFFFDDDD)
val red_errorContainer = Color(0xFFFFF0F0)

// Green theme
val green_primary = Color(0xFF2E7D32)
val green_primaryContainer = Color(0xFFC8E6C9)
val green_secondaryContainer = Color(0xFFE8F5E9)
val green_tertiaryContainer = Color(0xFFDCEDC8)
val green_errorContainer = Color(0xFFF1F8E9)

// Blue theme
val blue_primary = Color(0xFF1565C0)
val blue_primaryContainer = Color(0xFFBBDEFB)
val blue_secondaryContainer = Color(0xFFE3F2FD)
val blue_tertiaryContainer = Color(0xFFDDEEFF)
val blue_errorContainer = Color(0xFFE1F5FE)

// Yellow theme
val yellow_primary = Color(0xFFF9A825)
val yellow_primaryContainer = Color(0xFFFFECB3)
val yellow_secondaryContainer = Color(0xFFFFF8E1)
val yellow_tertiaryContainer = Color(0xFFFFFDE7)
val yellow_errorContainer = Color(0xFFFFFDE7)

// Black theme
val black_primary = Color(0xFF212121)
val black_primaryContainer = Color(0xFFBDBDBD)
val black_secondaryContainer = Color(0xFFE0E0E0)
val black_tertiaryContainer = Color(0xFFEEEEEE)
val black_errorContainer = Color(0xFFF5F5F5)

@Composable
fun getThemeColors(colorTheme: String): ColorScheme {
    val defaultColorScheme = MaterialTheme.colorScheme

    return when (colorTheme) {
        SettingsDataStore.COLOR_RED -> defaultColorScheme.copy(
            primary = red_primary,
            primaryContainer = red_primaryContainer,
            secondaryContainer = red_secondaryContainer,
            tertiaryContainer = red_tertiaryContainer,
            errorContainer = red_errorContainer
        )
        SettingsDataStore.COLOR_GREEN -> defaultColorScheme.copy(
            primary = green_primary,
            primaryContainer = green_primaryContainer,
            secondaryContainer = green_secondaryContainer,
            tertiaryContainer = green_tertiaryContainer,
            errorContainer = green_errorContainer
        )
        SettingsDataStore.COLOR_BLUE -> defaultColorScheme.copy(
            primary = blue_primary,
            primaryContainer = blue_primaryContainer,
            secondaryContainer = blue_secondaryContainer,
            tertiaryContainer = blue_tertiaryContainer,
            errorContainer = blue_errorContainer
        )
        SettingsDataStore.COLOR_YELLOW -> defaultColorScheme.copy(
            primary = yellow_primary,
            primaryContainer = yellow_primaryContainer,
            secondaryContainer = yellow_secondaryContainer,
            tertiaryContainer = yellow_tertiaryContainer,
            errorContainer = yellow_errorContainer
        )
        SettingsDataStore.COLOR_BLACK -> defaultColorScheme.copy(
            primary = black_primary,
            primaryContainer = black_primaryContainer,
            secondaryContainer = black_secondaryContainer,
            tertiaryContainer = black_tertiaryContainer,
            errorContainer = black_errorContainer
        )
        else -> defaultColorScheme.copy(
            primary = md_theme_light_primary,
            primaryContainer = md_theme_light_primaryContainer,
            secondaryContainer = md_theme_light_secondaryContainer,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            errorContainer = md_theme_light_errorContainer
        )
    }
}