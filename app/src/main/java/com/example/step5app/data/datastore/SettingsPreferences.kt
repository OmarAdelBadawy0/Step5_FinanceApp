package com.example.step5app.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object SettingsPreferences {
    val THEME_KEY = stringPreferencesKey("theme_preference")
    val LANGUAGE_KEY = stringPreferencesKey("language_preference")
}