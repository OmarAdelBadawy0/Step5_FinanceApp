package com.example.step5app.presentation.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.data.repositories.AffiliateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val affiliateRepository: AffiliateRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow(ConnectionsUiState())
    val uiState: StateFlow<ConnectionsUiState> = _uiState

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = affiliateRepository.getUserAffiliateInfo()
            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    user = it.data.firstOrNull(),
                    errorMessage = null
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = it.message
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
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
}