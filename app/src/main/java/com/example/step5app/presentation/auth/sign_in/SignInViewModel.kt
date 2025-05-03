package com.example.step5app.presentation.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun onEmailChange(newEmail: String) {
        _loginUiState.update { it.copy(email = newEmail) }
    }

    fun onPasswordChange(newPassword: String) {
        _loginUiState.update { it.copy(password = newPassword) }
    }

    fun onPasswordVisibilityChange() {
        _loginUiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onRememberMeCheckedChange() {
        _loginUiState.update { it.copy(isRememberMeChecked = !it.isRememberMeChecked) }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Simulate API call
            delay(2000)
            if (email == "test@example.com" && password == "password") {
                _loginUiState.update { it.copy(isLoading = false, isSuccessLogin = true) }
            } else {
                _loginUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Invalid email or password"
                    )
                }
            }
        }
    }
}