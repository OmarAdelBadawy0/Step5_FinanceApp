package com.example.step5app.presentation.network

import com.example.step5app.data.local.UiText
import com.example.step5app.data.model.ConnectionData

data class ConnectionsUiState(
    val isLoading: Boolean = false,
    val message: UiText? = null,
    val firstName: String = "",
    val balance: Double = 0.0,
    val inviteCode: String? = null,
    val connections: List<ConnectionData> = emptyList(),
    val isAddingConnection: Boolean = false,
    val connectionAddingCode: String = "",
    val errorMessage: UiText? = null
)