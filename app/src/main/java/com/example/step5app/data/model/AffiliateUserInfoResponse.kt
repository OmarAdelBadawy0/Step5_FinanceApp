package com.example.step5app.data.model

data class AffiliateUserInfoResponse(
    val message: String,
    val data: List<AffiliateUserInfo>,
    val pagination: Pagination
)


data class AffiliateUserInfo(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val parentConnectionId: String?,
    val UserWallets: List<UserWallet>,
    val createdAt: String,
    val updatedAt: String
)

data class UserWallet(
    val balance: Double,
    val updatedAt: String
)