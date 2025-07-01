package com.example.step5app.presentation.auth.sign_in

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.R
import com.example.step5app.data.local.UserPreferences
import com.example.step5app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    @ApplicationContext private val context: Context
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

    fun clearError() {
        _loginUiState.update { it.copy(errorMessage = null) }
    }

    fun loginUser() {
        if (loginUiState.value.email.isBlank() || loginUiState.value.password.isBlank()) {
            _loginUiState.update { it.copy(errorMessage = context.getString(R.string.email_and_password_are_required)) }
            return
        }

        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.signIn(
                email = loginUiState.value.email,
                password = loginUiState.value.password
            )

            result.fold(
                onSuccess = { response ->
                    val token = response.data?.accessToken
                    if (token != null) {
                        userPreferences.saveAccessToken(token)
                        _loginUiState.update {
                            it.copy(isLoading = false, isSuccessLogin = true)
                        }
                    } else {
                        _loginUiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = context.getString(R.string.login_failed_missing_token)
                            )
                        }
                    }
                },
                onFailure = { e ->
                    val message = when {
                        e.message?.contains("401") == true -> context.getString(R.string.incorrect_password)
                        e.message?.contains("404") == true -> context.getString(R.string.user_not_found_or_email_not_confirmed)
                        else -> context.getString(R.string.login_failed, e.message)
                    }
                    _loginUiState.update { it.copy(isLoading = false, errorMessage = message) }
                }
            )
        }
    }
}