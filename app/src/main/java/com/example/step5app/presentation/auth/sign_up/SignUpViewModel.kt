package com.example.step5app.presentation.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState: StateFlow<SignUpUiState> = _signUpUiState

    fun updateFirstName(newFirstName: String) {
        _signUpUiState.update { it.copy(firstName = newFirstName) }
    }

    fun updateLastName(newLastName: String) {
        _signUpUiState.update { it.copy(lastName = newLastName) }
    }

    fun updateEmail(newEmail: String) {
        _signUpUiState.update { it.copy(email = newEmail) }
    }

    fun updatePassword(newPassword: String) {
        _signUpUiState.update { it.copy(password = newPassword) }
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _signUpUiState.update { it.copy(confirmPassword = newConfirmPassword) }
    }

    fun updateIsPasswordVisible(newPasswordVisibility: Boolean) {
        _signUpUiState.update { it.copy(isPasswordVisible = newPasswordVisibility) }
    }

    fun updateIsConfirmPasswordVisible(newConfirmPasswordVisibility: Boolean) {
        _signUpUiState.update { it.copy(isConfirmPasswordVisible = newConfirmPasswordVisibility) }
    }

    fun updateIsTermsChecked(newTermsChecked: Boolean) {
        _signUpUiState.update { it.copy(isTermsChecked = newTermsChecked) }
    }

    fun signUp() {
        if (!validateForm()) return

        viewModelScope.launch {
            _signUpUiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching {
                authRepository.signUp(
                    firstName = signUpUiState.value.firstName,
                    lastName = signUpUiState.value.lastName,
                    email = signUpUiState.value.email,
                    password = signUpUiState.value.password
                )
            }.fold(
                onSuccess = {
                    _signUpUiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccessSignUp = true,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { e ->
                    _signUpUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Sign up failed. Please try again."
                        )
                    }
                }
            )
        }
    }

    private fun validateForm(): Boolean {
        return when {
            signUpUiState.value.firstName.isBlank() -> {
                _signUpUiState.update { it.copy(errorMessage = "Please enter your name") }
                false
            }
            signUpUiState.value.lastName.isBlank() -> {
                _signUpUiState.update { it.copy(errorMessage = "Please enter your last name") }
                false
            }
            signUpUiState.value.email.isBlank() -> {
                _signUpUiState.update { it.copy(errorMessage = "Please enter your email") }
                false
            }
            !signUpUiState.value.email.contains("@") -> {
                _signUpUiState.update { it.copy(errorMessage = "Please enter a valid email") }
                false
            }
            signUpUiState.value.password.isBlank() -> {
                _signUpUiState.update { it.copy(errorMessage = "Please enter your password") }
                false
            }
            signUpUiState.value.password.length < 6 -> {
                _signUpUiState.update { it.copy(errorMessage = "Password must be at least 6 characters") }
                false
            }
            signUpUiState.value.password != signUpUiState.value.confirmPassword -> {
                _signUpUiState.update { it.copy(errorMessage = "Passwords do not match") }
                false
            }
            !signUpUiState.value.isTermsChecked -> {
                _signUpUiState.update { it.copy(errorMessage = "Please accept the terms and conditions") }
                false
            }
            else -> true
        }
    }

    fun clearError() {
        _signUpUiState.update { it.copy(errorMessage = null) }
    }

}