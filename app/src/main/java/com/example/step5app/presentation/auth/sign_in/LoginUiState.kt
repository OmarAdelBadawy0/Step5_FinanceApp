package com.example.step5app.presentation.auth.sign_in

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isRememberMeChecked: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val errorMessage: String? = null
)
