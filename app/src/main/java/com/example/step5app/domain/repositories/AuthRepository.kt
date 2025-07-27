package com.example.step5app.domain.repositories

import com.example.step5app.data.model.ChangePasswordRequest
import com.example.step5app.data.model.ConfirmOtpResponse
import com.example.step5app.data.model.ProfileResponse
import com.example.step5app.data.model.SignInResponse
import com.example.step5app.data.model.UpdateProfileRequest

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

    suspend fun getProfile(token: String): Result<ProfileResponse>

    suspend fun updateProfile(request: UpdateProfileRequest, token: String): Result<ProfileResponse>

    suspend fun changePassword(request: ChangePasswordRequest): Result<Unit>

}