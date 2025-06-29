package com.example.step5app.data.remote

import com.example.step5app.data.model.AuthResponse
import com.example.step5app.data.model.ConfirmOtpRequest
import com.example.step5app.data.model.ConfirmOtpResponse
import com.example.step5app.data.model.SignInRequest
import com.example.step5app.data.model.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signin")
    suspend fun signIn(@Body request: SignInRequest): AuthResponse

    @POST("/auth/register")
    suspend fun signUp(
        @Body request: SignUpRequest,
        @Header("Accept-Language") language: String): AuthResponse

    @POST("/auth/confirm-register")
    suspend fun confirmOtp(
        @Body request: ConfirmOtpRequest,
        @Header("Accept-Language") language: String = "en"
    ): ConfirmOtpResponse
}