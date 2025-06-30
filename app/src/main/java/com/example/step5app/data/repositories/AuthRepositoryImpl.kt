package com.example.step5app.data.repositories

import com.example.step5app.data.local.UserPreferences
import com.example.step5app.data.model.SignUpRequest
import com.example.step5app.data.remote.AuthService
import com.example.step5app.domain.repositories.AuthRepository
import javax.inject.Inject
import com.example.step5app.data.model.ConfirmOtpRequest
import com.example.step5app.data.model.ConfirmOtpResponse

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,  // API service
    private val userPreferences: UserPreferences,
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return Result.success(Unit)
//        return try {
//            val response = authService.signIn(SignInRequest(email, password))
//            if (response.success && !response.token.isNullOrEmpty()) {
//                preferences.edit() { putString("auth_token", response.token) }
//                Result.success(Unit)
//            } else {
//                Result.failure(Exception(response.error ?: "Sign-in failed"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
    }

    override suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val response = authService.signUp(
                SignUpRequest(firstName, lastName, email, password), "en"
            )
            if (response.success) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message?.joinToString(", ") ?: "Sign-up failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun confirmOtp(
        email: String,
        verificationCode: String
    ): Result<ConfirmOtpResponse> {
        return try {
            val response = authService.confirmOtp(
                ConfirmOtpRequest(email, verificationCode),
                language = "en"
            )

            // Save the access token to DataStore
            userPreferences.clearAccessToken()
            userPreferences.saveAccessToken(response.data?.accessToken ?: "")

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override fun isUserLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }
}