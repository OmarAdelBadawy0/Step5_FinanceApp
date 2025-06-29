package com.example.step5app.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.step5app.data.datastore.Tokens
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val accessToken: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[Tokens.ACCESS_TOKEN_KEY] }

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { prefs -> prefs[Tokens.ACCESS_TOKEN_KEY] = token }
    }

    suspend fun getAccessTokenOnce(): String? {
        return accessToken.first()
    }

    suspend fun clearAccessToken() {
        context.dataStore.edit { prefs -> prefs.remove(Tokens.ACCESS_TOKEN_KEY) }
    }
}
