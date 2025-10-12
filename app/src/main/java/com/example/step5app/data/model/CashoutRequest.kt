package com.example.step5app.data.model

data class CashoutRequest(
    val provider: String,
    val handle: String
)

data class CashoutResponse(
    val message: String,
    val data: CashoutData?
)

data class CashoutData(
    val id: Int,
    val userId: String,
    val handle: String,
    val provider: String?,
    val notes: String?,
    val status: String?,
    val createdAt: String,
    val updatedAt: String
)
