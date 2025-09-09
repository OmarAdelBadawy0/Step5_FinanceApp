package com.example.step5app.data.model

data class ConnectionsResponse(
    val message: String,
    val data: List<ConnectionData>,
    val pagination: Pagination? = null
)

data class ConnectionData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val ChildrenConnections: List<ChildConnection> = emptyList(),
    val UserWallets: List<UserWallet> = emptyList()
)

data class ChildConnection(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val role: Any?
)