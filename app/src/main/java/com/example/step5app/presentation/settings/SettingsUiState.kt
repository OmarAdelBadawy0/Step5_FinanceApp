package com.example.step5app.presentation.settings

// presentation/settings/SettingsUiState.kt
data class SettingsUiState(
    val themePreference: String = "System", // "Dark", "Light", or "System"
    val language: String = "EN", // "EN" or "AR"
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    // Helper functions for state management
    fun copyWithLoading(loading: Boolean): SettingsUiState =
        this.copy(isLoading = loading)

    fun copyWithError(error: String?): SettingsUiState =
        this.copy(errorMessage = error)
}
