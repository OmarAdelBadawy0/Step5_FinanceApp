package com.example.step5app.data.model

data class AuthResponse(
    val success: Boolean,
    val token: String? = null,
    val error: String? = null
)
