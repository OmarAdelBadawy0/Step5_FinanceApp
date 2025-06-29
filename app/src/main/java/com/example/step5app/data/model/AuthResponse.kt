package com.example.step5app.data.model


data class AuthResponse(
    val success: Boolean,
    val message: List<String>?,
    val error: String?,
    val statusCode: Int?
)
