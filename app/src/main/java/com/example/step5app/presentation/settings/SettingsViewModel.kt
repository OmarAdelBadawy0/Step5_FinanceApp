package com.example.step5app.presentation.settings

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.domain.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _uiState.update { it.copyWithLoading(true) }

            try {
                val theme = settingsRepository.getThemePreference()
                val language = settingsRepository.getLanguagePreference()

                _uiState.update {
                    it.copy(
                        themePreference = theme,
                        language = language,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copyWithLoading(false)
                        .copyWithError("Failed to load settings")
                }
            }
        }
    }

    fun updateTheme(theme: String) {
        viewModelScope.launch {
            _uiState.update { it.copyWithLoading(true) }

            try {
                settingsRepository.updateThemePreference(theme)
                _uiState.update {
                    it.copy(
                        themePreference = theme,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copyWithLoading(false)
                        .copyWithError("Failed to update theme")
                }
            }
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            _uiState.update { it.copyWithLoading(true) }

            try {
                settingsRepository.updateLanguagePreference(language)
                _uiState.update {
                    it.copy(
                        language = language,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copyWithLoading(false)
                        .copyWithError("Failed to update language")
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun setLanguage(context: Context, language: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(language)
        }else{
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
        }
    }

    fun setTheme(theme: String){
        when (theme.lowercase()) {
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun restartApp(context: Context) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
//        Runtime.getRuntime().exit(0) // kills the current process
    }

}