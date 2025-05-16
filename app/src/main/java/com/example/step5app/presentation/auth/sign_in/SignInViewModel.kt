package com.example.step5app.presentation.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

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

    fun loginUser() {
        val currentState = _loginUiState.value
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching {
                authRepository.signIn(
                    email = currentState.email,
                    password = currentState.password
                )
            }.onSuccess {
                _loginUiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccessLogin = true,
                        errorMessage = null
                    )
                }
            }.onFailure { e ->
                _loginUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Login failed"
                    )
                }
            }
        }
    }
}