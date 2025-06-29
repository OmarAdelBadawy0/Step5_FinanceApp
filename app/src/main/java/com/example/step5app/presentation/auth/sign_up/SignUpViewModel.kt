package com.example.step5app.presentation.auth.sign_up

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.R
import com.example.step5app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
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
                            val copy = it.copy(
                                isLoading = false,
                                errorMessage = context.getString(R.string.email_already_exists_or_awaiting_otp_confirmation),
                                showOtpDialog = true
                            )
                            copy
                        }else{
                            it.copy(
                                isLoading = false,
                                errorMessage = context.getString(R.string.otp_sent_to_your_email),
                                showOtpDialog = true
                            )
                        }

                    }
                },
                onFailure = { e ->
                    _signUpUiState.update {
                        it.copy(
                            errorMessage = e.message  ?: context.getString(R.string.sign_up_failed_please_try_again),
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

    fun confirmOtp(email: String, code: String) {
        viewModelScope.launch {
            _signUpUiState.update { it.copy(isLoading = true, errorMessage = null, isSuccessSignUp = false) }

            val result = authRepository.confirmOtp(email, code)
            result.fold(
                onSuccess = { response ->
                    _signUpUiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccessSignUp = true,
                            showOtpDialog = false,
                            errorMessage = context.getString(R.string.otp_confirmed_successfully)
                        )
                    }
                },
                onFailure = { error ->
                    _signUpUiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccessSignUp = false,
                            errorMessage =
                            if (error.message?.contains("401") == true){
                                context.getString(R.string.incorrect_otp_code)
                            }else if (error.message?.contains("404") == true){
                                context.getString(R.string.expired_verification_code)
                            }else if (error.message?.contains("400") == true){
                                context.getString(R.string.invalid_otp_format_must_be_6_digit_number)
                            } else{
                                error.message ?: context.getString(R.string.otp_confirmation_failed)

                            }
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
                _signUpUiState.update { it.copy(errorMessage = context.getString(R.string.please_enter_your_name)) }
                false
            }
            signUpUiState.value.lastName.isBlank() -> {
                _signUpUiState.update { it.copy(errorMessage = context.getString(R.string.please_enter_your_last_name)) }
                false
            }
            signUpUiState.value.email.isBlank() -> {
                _signUpUiState.update { it.copy(errorMessage = context.getString(R.string.please_enter_your_email)) }
                false
            }
            !signUpUiState.value.email.contains("@") -> {
                _signUpUiState.update { it.copy(errorMessage = context.getString(R.string.please_enter_a_valid_email)) }
                false
            }
            signUpUiState.value.password.isBlank() -> {
                _signUpUiState.update { it.copy(errorMessage = context.getString(R.string.please_enter_your_password)) }
                false
            }
            signUpUiState.value.password.length < 8 -> {
                _signUpUiState.update { it.copy(errorMessage = context.getString(R.string.password_must_be_at_least_8_characters)) }
                false
            }
            !signUpUiState.value.password.matches(passwordPattern) -> {
                _signUpUiState.update {
                    it.copy(errorMessage = context.getString(R.string.password_must_contain_uppercase_lowercase_number_and_symbol))
                }
                false
            }
            signUpUiState.value.password != signUpUiState.value.confirmPassword -> {
                _signUpUiState.update { it.copy(errorMessage = context.getString(R.string.passwords_do_not_match)) }
                false
            }
            !signUpUiState.value.isTermsChecked -> {
                _signUpUiState.update { it.copy(errorMessage = context.getString(R.string.please_accept_the_terms_and_conditions)) }
                false
            }
            else -> true
        }
    }

    fun clearError() {
        _signUpUiState.update { it.copy(errorMessage = null) }
    }

}