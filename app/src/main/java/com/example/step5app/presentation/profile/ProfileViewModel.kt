package com.example.step5app.presentation.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.R
import com.example.step5app.data.local.UserPreferences
import com.example.step5app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
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

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearAccessToken()
        }
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

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val token = userPreferences.getAccessTokenOnce()
                if (token.isNullOrBlank()) {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = context.getString(R.string.unauthorized))
                    }
                    return@launch
                }

                val result = authRepository.getProfile(token)
                result.fold(
                    onSuccess = { response ->
                        val data = response.data
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                firstName = data.firstName,
                                lastName = data.lastName,
                                email = data.email,
                                phoneNumber = data.phoneNumber ?: it.phoneNumber
                            )
                        }
                    },
                    onFailure = { e ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = e.message ?: context.getString(R.string.failed_to_load_profile)
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
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