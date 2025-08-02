package com.example.step5app.data.model

data class VerifyForgetPasswordRequest(
    val email: String,
    val verificationCode: String
)