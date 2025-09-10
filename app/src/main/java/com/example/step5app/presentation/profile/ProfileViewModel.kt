package com.example.step5app.presentation.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.R
import com.example.step5app.data.local.UserPreferences
import com.example.step5app.data.model.ChangePasswordRequest
import com.example.step5app.data.model.UpdateProfileRequest
import com.example.step5app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.Regex

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val authRepository: AuthRepository,
    @ApplicationContext internal val context: Context
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
            val currentState = uiState.value
            try {
                val token = userPreferences.getAccessTokenOnce()
                if (token.isNullOrBlank()) {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Unauthorized")
                    }
                    return@launch
                }

                val request = UpdateProfileRequest(
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    phoneNumber = currentState.phoneNumber ?: ""
                )

                val result = authRepository.updateProfile(
                    request = request,
                    token = token
                )

                result.fold(
                    onSuccess = { response ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isProfileUpdated = true,
                                errorMessage = context.getString(R.string.profile_updated_successfully),
                                firstName = response.data.firstName,
                                lastName = response.data.lastName,
                                email = response.data.email,
                                phoneNumber = response.data.phoneNumber ?: it.phoneNumber,
                            )
                        }
                    },
                    onFailure = { e ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = context.getString(R.string.phone_number_should_start_with_2_and_not_empty)
                            )
                        }
                    }
                )
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
            val currentState = uiState.value
            _uiState.update { it.copy(isLoading = true) }
            try {
                // Validate password
                if (!validatePassword(currentState.newPassword, currentState.confirmPassword)) {
                    return@launch
                }

                val request = ChangePasswordRequest(
                    email = currentState.email,
                    oldPassword = currentState.oldPassword,
                    newPassword = currentState.newPassword
                )
                val result = authRepository.changePassword(request)

                result.fold(
                    onSuccess = {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isPasswordChanged = true,
                                oldPassword = "",
                                newPassword = "",
                                confirmPassword = "",
                                errorMessage = context.getString(R.string.password_changed_successfully)
                            )
                        }
                    },
                    onFailure = { e ->
                        var errorMsg: String? = null
                        if (e.message?.contains("401") == true) {
                            errorMsg = context.getString(R.string.the_old_password_is_incorrect)
                        }else if (e.message?.contains("404") == true) {
                            errorMsg = context.getString(R.string.user_not_found)
                        }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = errorMsg ?: e.message ?: context.getString(R.string.failed_to_change_password)
                            )
                        }
                    }
                )


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

                val result = authRepository.getProfile()
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

    fun deleteUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.deleteUser()
            result.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
                userPreferences.clearAccessToken()
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
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

    fun validatePassword(password: String, confirmPassword: String): Boolean {
        val passwordPattern =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}\$")


        return when {
            password.isBlank() -> {
                _uiState.update { it.copy(errorMessage = context.getString(R.string.please_enter_your_password)) }
                false
            }
            password.length < 8 -> {
                _uiState.update { it.copy(errorMessage = context.getString(R.string.password_must_be_at_least_8_characters)) }
                false
            }
            !password.matches(passwordPattern) -> {
                _uiState.update {
                    it.copy(errorMessage = context.getString(R.string.password_must_contain_uppercase_lowercase_number_and_symbol))
                }
                false
            }
            password != confirmPassword -> {
                _uiState.update { it.copy(errorMessage = context.getString(R.string.passwords_do_not_match)) }
                false
            }
            else -> true
        }

    }


}