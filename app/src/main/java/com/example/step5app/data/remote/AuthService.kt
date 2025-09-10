package com.example.step5app.data.remote

import com.example.step5app.data.model.AuthResponse
import com.example.step5app.data.model.ChangeForgetPasswordRequest
import com.example.step5app.data.model.ChangePasswordRequest
import com.example.step5app.data.model.ConfirmOtpRequest
import com.example.step5app.data.model.ConfirmOtpResponse
import com.example.step5app.data.model.ProfileResponse
import com.example.step5app.data.model.RequestForgetPasswordRequest
import com.example.step5app.data.model.SignInRequest
import com.example.step5app.data.model.SignInResponse
import com.example.step5app.data.model.SignUpRequest
import com.example.step5app.data.model.UpdateProfileRequest
import com.example.step5app.data.model.VerifyForgetPasswordRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/login")
    suspend fun login(
        @Body request: SignInRequest,
        @Header("Accept-Language") language: String = "en"
    ): SignInResponse

    @POST("/auth/register")
    suspend fun signUp(
        @Body request: SignUpRequest,
        @Header("Accept-Language") language: String): AuthResponse

    @POST("/auth/confirm-register")
    suspend fun confirmOtp(
        @Body request: ConfirmOtpRequest,
        @Header("Accept-Language") language: String = "en"
    ): ConfirmOtpResponse

    @GET("/auth/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
        @Header("Accept-Language") language: String = "en"
    ): ProfileResponse


    @PATCH("auth/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Header("Accept-Language") language: String = "en",
        @Body body: UpdateProfileRequest
    ): Response<ProfileResponse>

    @PATCH("auth/change-password")
    suspend fun changePassword(
        @Header("Accept-Language") language: String = "en",
        @Body body: ChangePasswordRequest
    ): Response<Unit>

    @POST("/auth/request-forget-password")
    suspend fun requestForgetPassword(
        @Body request: RequestForgetPasswordRequest,
        @Header("Accept-Language") language: String = "en"
    ): Response<Unit>

    @POST("/auth/verify-forget-password")
    suspend fun verifyForgetPassword(
        @Body request: VerifyForgetPasswordRequest,
        @Header("Accept-Language") language: String = "en"
    ): Response<Unit>

    @PATCH("/auth/change-forget-password")
    suspend fun changeForgetPassword(
        @Body request: ChangeForgetPasswordRequest,
        @Header("Accept-Language") language: String = "en"
    ): Response<Unit>

    @DELETE("users")
    suspend fun deleteUser(
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): Response<Unit>
}