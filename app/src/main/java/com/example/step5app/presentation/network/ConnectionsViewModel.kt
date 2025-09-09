package com.example.step5app.presentation.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.R
import com.example.step5app.data.local.UiText
import com.example.step5app.data.repositories.AffiliateRepository
import com.example.step5app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val affiliateRepository: AffiliateRepository,
    private val profileRepository: AuthRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow(ConnectionsUiState())
    val uiState: StateFlow<ConnectionsUiState> = _uiState

    init {
        getUserInfo()
        getConnections()
    }

    fun onConnectionAddingCodeChange(code: String) {
        _uiState.value = _uiState.value.copy(connectionAddingCode = code)
    }

    fun onIsAddingConnectionChange(isAdding: Boolean) {
        _uiState.value = _uiState.value.copy(isAddingConnection = isAdding)
    }

    fun onMessageShown() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    fun onErrorMessageShown() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun getUserInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = profileRepository.getProfile()
            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    firstName = it.data.firstName,
                    balance = it.data.userWallets?.firstOrNull()?.balance ?: 0.0,
                    errorMessage = null
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = UiText.DynamicString(it.message ?: "Unknown error")
                )
            }
        }
    }

    fun fetchInviteCode() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val response = affiliateRepository.getInviteCode()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    inviteCode = response?.data?.inviteCode,
                    errorMessage = if (response?.data?.inviteCode == null){
                        UiText.StringResource(R.string.user_already_has_a_parent_connection_cannot_create_another_invite_code)
                    }else{ null }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = UiText.DynamicString(e.message ?: "Unknown error")
                )
            }
        }
    }

    fun getConnections() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = affiliateRepository.getConnections()
            result.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        connections = response,
                        errorMessage = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.DynamicString(throwable.message ?: "Unknown error")
                    )
                }
            }
        }
    }

    fun addConnection() {
        val code = _uiState.value.connectionAddingCode
        if (code.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = affiliateRepository.addConnection(code)

            result.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        message = UiText.StringResource(R.string.connections_added_successfully),
                        connectionAddingCode = "",
                        isAddingConnection = false,
                        isLoading = false
                    )
                }
            }.onFailure { e ->
                if (e.message?.contains("400") == true){
                    _uiState.update {
                        it.copy(
                            message = UiText.StringResource(R.string.invitecode_must_be_8_characters)
                        )
                    }
                }else if (e.message?.contains("404") == true){
                    _uiState.update {
                        it.copy(
                            message = UiText.StringResource(R.string.invalid_or_expired_invite_code)
                        )
                    }
                }else{
                    _uiState.update {
                        it.copy(
                            errorMessage = UiText.DynamicString(e.message ?: "Unknown error")
                        )
                    }
                }
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}