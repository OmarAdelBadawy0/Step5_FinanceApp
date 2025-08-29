package com.example.step5app.domain.model

data class Plan(
    val id: Int,
    val name: String,
    val description: String?,
    val price: Double,
    val annualDiscount: Double,
    val createdAt: String,
    val updatedAt: String
)