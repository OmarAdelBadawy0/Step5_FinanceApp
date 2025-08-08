package com.example.step5app.presentation.auth.forgot_password

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.R
import com.example.step5app.data.model.ChangeForgetPasswordRequest
import com.example.step5app.data.model.RequestForgetPasswordRequest
import com.example.step5app.data.model.VerifyForgetPasswordRequest
import com.example.step5app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    data class ForgotScreens(
        val screen: Int = 1,
    )

    private val _email = mutableStateOf("")
    val email: String
        get() = _email.value

    private val _password = mutableStateOf("")
    val password: String
        get() = _password.value

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: String
        get() = _confirmPassword.value

    private val _forgotScreensState = mutableStateOf(ForgotScreens())
    val forgotScreensState: ForgotScreens
        get() = _forgotScreensState.value

    private val _otpCode = mutableStateOf("")
    val otpCode: String
        get() = _otpCode.value

    private val _isPasswordVisible = mutableStateOf(false)
    val isPasswordVisible: Boolean
        get() = _isPasswordVisible.value

    private val _isConfirmPasswordVisible = mutableStateOf(false)
    val isConfirmPasswordVisible: Boolean
        get() = _isConfirmPasswordVisible.value

    private val _errorMessage = mutableStateOf("")
    val errorMessage: String
        get() = _errorMessage.value

    private val _passwordChangedSuccess = mutableStateOf(false)
    val passwordChangedSuccess: Boolean
        get() = _passwordChangedSuccess.value


    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun updateForgotScreensState(newForgotScreensState: ForgotScreens) {
        _forgotScreensState.value = newForgotScreensState
    }

    fun updateOtpCode(newOtpCode: String) {
        _otpCode.value = newOtpCode
    }

    fun updateIsPasswordVisible(newPasswordVisibility: Boolean) {
        _isPasswordVisible.value = newPasswordVisibility
    }

    fun updateIsConfirmPasswordVisible(newConfirmPasswordVisibility: Boolean) {
        _isConfirmPasswordVisible.value = newConfirmPasswordVisibility
    }

    fun updateErrorMessage(newErrorMessage: String) {
        _errorMessage.value = newErrorMessage
    }

    fun clearError() {
        _errorMessage.value = ""
    }

    fun validateEmail(): Boolean {
        return _email.value.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
    }

    fun validatePassword(): Boolean {
        val passwordPattern =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}\$")

        if (_password.value.length < 8) {
            updateErrorMessage(context.getString(R.string.password_must_be_at_least_8_characters))
            return false
        }

        if (!_password.value.matches(passwordPattern)) {
            updateErrorMessage(context.getString(R.string.password_must_contain_uppercase_lowercase_number_and_symbol))
            return false
        }

        return true
    }

    fun requestForgetPassword() {
        viewModelScope.launch {

            val result = authRepository.requestForgetPassword(
                RequestForgetPasswordRequest(email = _email.value)
            )

            result.fold(
                onSuccess = {
                    updateForgotScreensState(
                        ForgotScreens(
                            screen = 2
                        )
                    )
                },
                onFailure = { e ->
                    updateForgotScreensState(
                        ForgotScreens(
                            screen = 2
                        )
                    )
//                    _errorMessage.value =  "Invalid Email"
                }
            )
        }
    }


    fun verifyForgetPassword() {
        viewModelScope.launch {

            val request = VerifyForgetPasswordRequest(
                email = _email.value,
                verificationCode = _otpCode.value
            )

            val result = authRepository.verifyForgetPassword(request)

            result.fold(
                onSuccess = {
                    updateForgotScreensState(
                        ForgotScreens(
                            screen = 3
                        )
                    )
                },
                onFailure = { e ->
                    if (e.message?.contains("400") == true || e.message?.contains("401") == true) {
                        updateErrorMessage(context.getString(R.string.invalid_otp))
                    } else if (e.message?.contains("404") == true) {
                        updateErrorMessage(context.getString(R.string.expired_verification_code))
                    } else {
                        updateErrorMessage(context.getString(R.string.unexpected_error_happened))
                    }
                }
            )
        }
    }

    fun changeForgetPassword() {
        if (!validatePassword()) return
        viewModelScope.launch {

            val request = ChangeForgetPasswordRequest(
                email = _email.value,
                password = _password.value
            )

            val result = authRepository.changeForgetPassword(request)

            result.fold(
                onSuccess = {
                    updateForgotScreensState(
                        ForgotScreens(
                            screen = 1
                        )
                    )
                    updateErrorMessage(context.getString(R.string.password_changed_successfully))
                    _passwordChangedSuccess.value = true
                },
                onFailure = { e ->
                    if (e.message?.contains("404") == true) {
                        updateErrorMessage(context.getString(R.string.password_reset_request_expired))
                    } else {
                        updateErrorMessage(context.getString(R.string.unexpected_error_happened))
                    }
                }
            )
        }
    }



}