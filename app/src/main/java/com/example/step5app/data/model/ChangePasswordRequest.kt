package com.example.step5app.data.model

data class ChangePasswordRequest(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)

