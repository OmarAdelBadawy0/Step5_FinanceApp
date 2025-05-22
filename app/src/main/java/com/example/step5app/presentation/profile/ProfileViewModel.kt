package com.example.step5app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun updateFirstName(firstName: String) {
        _uiState.update { it.copy(firstName = firstName) }
    }

    fun updateLastName(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updateOldPassword(oldPassword: String) {
        _uiState.update { it.copy(oldPassword = oldPassword) }
    }

    fun updateNewPassword(newPassword: String) {
        _uiState.update { it.copy(newPassword = newPassword) }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun toggleOldPasswordVisibility() {
        _uiState.update { it.copy(isOldPasswordVisible = !it.isOldPasswordVisible) }
    }

    fun toggleNewPasswordVisibility() {
        _uiState.update { it.copy(isNewPasswordVisible = !it.isNewPasswordVisible) }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    fun updateSelectedTabIndex(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun saveProfileChanges() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // TODO: Add your API call or database operation here
                // Simulate network/database operation
                kotlinx.coroutines.delay(1000)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isProfileUpdated = true,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to update profile"
                    )
                }
            }
        }
    }

    fun changePassword() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // Validate passwords
                if (_uiState.value.newPassword != _uiState.value.confirmPassword) {
                    throw IllegalArgumentException("Passwords don't match")
                }

                if (_uiState.value.newPassword.length < 6) {
                    throw IllegalArgumentException("Password must be at least 6 characters")
                }

                // TODO: Add your password change API call here
                // Simulate network/database operation
                kotlinx.coroutines.delay(1000)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isPasswordChanged = true,
                        oldPassword = "",
                        newPassword = "",
                        confirmPassword = "",
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to change password"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun clearSuccessStates() {
        _uiState.update {
            it.copy(
                isPasswordChanged = false,
                isProfileUpdated = false
            )
        }
    }
}