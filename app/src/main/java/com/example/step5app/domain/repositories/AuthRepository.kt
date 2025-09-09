package com.example.step5app.domain.repositories

import com.example.step5app.data.model.ChangeForgetPasswordRequest
import com.example.step5app.data.model.ChangePasswordRequest
import com.example.step5app.data.model.ConfirmOtpResponse
import com.example.step5app.data.model.ProfileResponse
import com.example.step5app.data.model.RequestForgetPasswordRequest
import com.example.step5app.data.model.SignInResponse
import com.example.step5app.data.model.UpdateProfileRequest
import com.example.step5app.data.model.VerifyForgetPasswordRequest

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

    suspend fun getProfile(): Result<ProfileResponse>

    suspend fun updateProfile(request: UpdateProfileRequest, token: String): Result<ProfileResponse>

    suspend fun changePassword(request: ChangePasswordRequest): Result<Unit>

    suspend fun requestForgetPassword(request: RequestForgetPasswordRequest): Result<Unit>

    suspend fun verifyForgetPassword(request: VerifyForgetPasswordRequest): Result<Unit>

    suspend fun changeForgetPassword(request: ChangeForgetPasswordRequest): Result<Unit>
}