package com.example.step5app.data.local

data class SubscriptionRequest(
    val planId: Int,
    val isAnnual: Boolean
)

data class SubscriptionResponse(
    val message: String,
    val data: SubscriptionData
)

data class SubscriptionData(
    val orderId: Int,
    val amountCents: Int,
    val paymentUrl: String
)