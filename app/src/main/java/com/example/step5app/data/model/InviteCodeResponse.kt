package com.example.step5app.data.model

data class InviteCodeResponse(
    val message: String,
    val data: InviteCodeData,
    val pagination: Any? = null
)

data class InviteCodeData(
    val inviteCode: String
)
