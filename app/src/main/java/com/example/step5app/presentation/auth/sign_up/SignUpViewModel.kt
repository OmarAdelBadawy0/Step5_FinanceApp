package com.example.step5app.presentation.auth.sign_up

import android.util.Log
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

    fun updateOtpCode(newOtpCode: String) {
        _signUpUiState.update { it.copy(otpCode = newOtpCode) }
    }

    fun updateShowOtpDialog(newShowOtpDialog: Boolean) {
        _signUpUiState.update { it.copy(showOtpDialog = newShowOtpDialog) }
    }

    fun signUp() {
        if (!validateForm()) return

        viewModelScope.launch {
            _signUpUiState.update { it.copy(isLoading = true, errorMessage = null, isSuccessSignUp = false, showOtpDialog = false) }
            runCatching {
                authRepository.signUp(
                    firstName = signUpUiState.value.firstName,
                    lastName = signUpUiState.value.lastName,
                    email = signUpUiState.value.email,
                    password = signUpUiState.value.password
                )
            }.fold(
                onSuccess = { result ->
                    _signUpUiState.update {
                        Log.d("SignUpViewModel", "signUp result: $result.")
                        if (result.toString().contains("HTTP 409 Conflict")){
                            it.copy(
                                isLoading = false,
                                errorMessage = "Email already exists or Awaiting OTP confirmation",
                                showOtpDialog = true
                            )
                        }else{
                            it.copy(
                                isLoading = false,
                                errorMessage = "OTP Sent to Your Email",
                                showOtpDialog = true
                            )
                        }

                    }
                },
                onFailure = { e ->
                    _signUpUiState.update {
                        it.copy(
                            errorMessage = e.message  ?: "Sign up failed. Please try again.",
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

    fun confirmOtp(email: String, code: String) {
        viewModelScope.launch {
            _signUpUiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.confirmOtp(email, code)
            result.fold(
                onSuccess = { response ->
                    _signUpUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "OTP confirmed successfully"
                        )
                    }
                },
                onFailure = { error ->
                    _signUpUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "OTP confirmation failed"
                        )
                    }
                }
            )
        }
    }


    private fun validateForm(): Boolean {
        val passwordPattern =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}\$")
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
            signUpUiState.value.password.length < 8 -> {
                _signUpUiState.update { it.copy(errorMessage = "Password must be at least 8 characters") }
                false
            }
            !signUpUiState.value.password.matches(passwordPattern) -> {
                _signUpUiState.update {
                    it.copy(errorMessage = "Password must contain uppercase, lowercase, number, and symbol")
                }
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