package com.example.step5app.presentation.settings

// presentation/settings/SettingsViewModel.kt
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
}