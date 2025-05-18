package com.example.step5app.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.step5app.data.datastore.SettingsPreferences
import com.example.step5app.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings_preferences"
)

class SettingsRepositoryImpl @Inject constructor(
    private val context: Context
) : SettingsRepository {

    override suspend fun getThemePreference(): String {
        return context.settingsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[SettingsPreferences.THEME_KEY] ?: "System"
            }
            .first() // Convert Flow<String> to String
    }

    override suspend fun updateThemePreference(theme: String) {
        context.settingsDataStore.edit { preferences ->
            preferences[SettingsPreferences.THEME_KEY] = theme
        }
    }

    override suspend fun getLanguagePreference(): String {
        return context.settingsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[SettingsPreferences.LANGUAGE_KEY] ?: "EN"
            }
            .first()
    }

    override suspend fun updateLanguagePreference(language: String) {
        context.settingsDataStore.edit { preferences ->
            preferences[SettingsPreferences.LANGUAGE_KEY] = language
        }
    }

    // Optional: Flow versions for reactive observation
    fun getThemePreferenceFlow(): Flow<String> {
        return context.settingsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[SettingsPreferences.THEME_KEY] ?: "System"
            }
    }

    fun getLanguagePreferenceFlow(): Flow<String> {
        return context.settingsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[SettingsPreferences.LANGUAGE_KEY] ?: "EN"
            }
    }
}