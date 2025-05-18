package com.example.step5app.domain.repositories

// domain/repositories/SettingsRepository.kt
interface SettingsRepository {
    suspend fun getThemePreference(): String
    suspend fun updateThemePreference(theme: String)

    suspend fun getLanguagePreference(): String
    suspend fun updateLanguagePreference(language: String)
}