package com.example.step5app.data.model

data class InviteCodeRequest(val inviteCode: String)

data class ApiResponse(
    val message: String,
    val data: Any?,
    val pagination: Any?
)