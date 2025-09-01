package com.example.step5app.presentation.network

import com.example.step5app.data.local.UiText
import com.example.step5app.data.model.AffiliateUserInfo

data class ConnectionsUiState(
    val isLoading: Boolean = false,
    val message: UiText? = null,
    val user: AffiliateUserInfo? = null,
    val inviteCode: String? = null,
    val isAddingConnection: Boolean = false,
    val connectionAddingCode: String = "",
    val errorMessage: UiText? = null
)