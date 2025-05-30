package com.example.step5app.domain.model

data class Subscription(
    val name: String,
    val price: Double,
    val duration: String,
    val features: List<String>,
    val isSubscribed: Boolean
)
