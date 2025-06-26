package com.example.step5app.presentation.auth.sign_up

data class SignUpUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isTermsChecked: Boolean = false,
    val showOtpDialog: Boolean = false,
    val otpCode: String = "",
    val isLoading: Boolean = false,
    val isSuccessSignUp: Boolean = false,
    val errorMessage: String? = null
)
