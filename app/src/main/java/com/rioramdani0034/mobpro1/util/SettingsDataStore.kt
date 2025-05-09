package com.rioramdani0034.mobpro1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class SettingsDataStore(private val context: Context) {
    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val THEME_COLOR = stringPreferencesKey("theme_color")

        // Available theme colors
        const val COLOR_DEFAULT = "default"
        const val COLOR_RED = "red"
        const val COLOR_GREEN = "green"
        const val COLOR_BLUE = "blue"
        const val COLOR_YELLOW = "yellow"
        const val COLOR_BLACK = "black"
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }

    val themeColorFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_COLOR] ?: COLOR_DEFAULT
    }

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }

    suspend fun saveThemeColor(colorName: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_COLOR] = colorName
        }
    }
}