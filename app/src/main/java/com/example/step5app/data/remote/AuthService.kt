package com.example.step5app.data.remote

import com.example.step5app.data.model.AuthResponse
import com.example.step5app.data.model.SignInRequest
import com.example.step5app.data.model.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signin")
    suspend fun signIn(@Body request: SignInRequest): AuthResponse

    @POST("auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse
}