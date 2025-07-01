package com.example.step5app.domain.repositories

import com.example.step5app.data.model.ConfirmOtpResponse
import com.example.step5app.data.model.SignInResponse

interface AuthRepository {
    suspend fun signIn(email: String, password: String):  Result<SignInResponse>
    suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<Unit>
    suspend fun confirmOtp(email: String, verificationCode: String): Result<ConfirmOtpResponse>

    suspend fun signOut()
    fun isUserLoggedIn(): Boolean
}