package com.example.step5app.domain.model

data class ConnectionData(
    val name: String,
    val profit: Double,
    val numOfPlans: Int,
    val connections: List<String>
)
