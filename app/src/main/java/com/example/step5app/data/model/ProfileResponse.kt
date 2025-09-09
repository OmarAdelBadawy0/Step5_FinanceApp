package com.example.step5app.data.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ProfileData,
    @SerializedName("pagination") val pagination: Pagination? = null
)

data class ProfileData(
    @SerializedName("id") val id: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("email") val email: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("UserWallets") val userWallets: List<UserWallet?>? = null,
    @SerializedName("UserSubscriptions") val userSubscriptions: List<UserSubscription?>? = null
)

data class UserWallet(
    @SerializedName("balance") val balance: Double,
    @SerializedName("updatedAt") val updatedAt: String
)

data class UserSubscription(
    @SerializedName("planId") val planId: Int,
    @SerializedName("subscribedAt") val subscribedAt: String,
    @SerializedName("expireAt") val expireAt: String
)