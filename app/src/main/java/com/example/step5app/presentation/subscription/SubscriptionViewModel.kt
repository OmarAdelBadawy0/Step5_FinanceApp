package com.example.step5app.presentation.subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.R
import com.example.step5app.data.local.SubscriptionRequest
import com.example.step5app.data.local.UiText
import com.example.step5app.data.repositories.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val planRepository: PlanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionUiState())
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    init {
        loadPlans()
    }

    fun loadPlans() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val plans = planRepository.fetchPlans()
                _uiState.update {
                    it.copy(
                        plans = plans,
                        isLoading = false,
                        selectedPlan = plans.firstOrNull()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.DynamicString(e.message ?: "Failed to load plans")
                    )
                }
            }
        }
    }

    fun makeSubscription(planId: Int, isAnnual: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSubscriptionInProgress = true,
                    errorMessage = null,
                    message = null
                )
            }

            try {
                val request = SubscriptionRequest(planId, isAnnual)
                val response = planRepository.makeSubscription(request)

                if (response.message.contains("Success")) {
                    _uiState.update {
                        it.copy(
                            subscribeRequestData = response.data,
//                            message = UiText.StringResource(R.string.subscription_subscriped_successful),
                            isSubscriptionInProgress = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = UiText.DynamicString(response.message),
                            isSubscriptionInProgress = false
                        )
                    }
                }
            } catch (e: Exception) {
                if (e.message.toString().contains("400")) {
                    _uiState.update {
                        it.copy(
                            errorMessage = UiText.StringResource(R.string.you_have_to_add_a_phone_number_to_your_profile_first),
                            isSubscriptionInProgress = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = UiText.DynamicString("$e Subscription failed"),
                            isSubscriptionInProgress = false
                        )
                    }
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // Helper extension function for updating state
    private fun MutableStateFlow<SubscriptionUiState>.update(transform: (SubscriptionUiState) -> SubscriptionUiState) {
        this.value = transform(this.value)
    }
}