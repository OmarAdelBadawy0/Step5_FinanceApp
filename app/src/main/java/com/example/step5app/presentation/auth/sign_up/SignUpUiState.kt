package com.example.step5app.presentation.auth.sign_up

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccessSignUp: Boolean = false,
    val errorMessage: String? = null
)
