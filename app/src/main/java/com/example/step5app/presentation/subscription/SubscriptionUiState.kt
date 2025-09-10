package com.example.step5app.presentation.subscription

import com.example.step5app.data.local.SubscriptionData
import com.example.step5app.data.local.UiText
import com.example.step5app.domain.model.Plan

data class SubscriptionUiState(
    val plans: List<Plan> = emptyList(),
    val myPlan: Plan? = null,
    val myPlanExpireAt: String? = null,
    val subscribeRequestData: SubscriptionData? = null,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val message: UiText? = null,
    val selectedPlan: Plan? = null,
    val isAnnualSelected: Boolean = false,
    val isSubscriptionInProgress: Boolean = false,
)
