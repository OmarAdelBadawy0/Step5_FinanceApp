package com.example.step5app.presentation.network

import com.example.step5app.data.model.AffiliateUserInfo

data class ConnectionsUiState(
    val isLoading: Boolean = false,
    val user: AffiliateUserInfo? = null,
    val inviteCode: String? = null,
    val errorMessage: String? = null
)