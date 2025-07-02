package com.example.step5app.data.model

data class ProfileResponse(
    val message: String,
    val data: ProfileData
)

data class ProfileData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
)