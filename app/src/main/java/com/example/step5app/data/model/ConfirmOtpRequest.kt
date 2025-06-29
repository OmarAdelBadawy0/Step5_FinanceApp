package com.example.step5app.data.model

data class ConfirmOtpRequest(
    val email: String,
    val verificationCode: String
)
