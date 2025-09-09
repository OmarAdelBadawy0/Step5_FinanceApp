package com.example.step5app.data.repositories

import com.example.step5app.data.local.UserPreferences
import com.example.step5app.data.model.ChangeForgetPasswordRequest
import com.example.step5app.data.model.ChangePasswordRequest
import com.example.step5app.data.model.SignUpRequest
import com.example.step5app.data.remote.AuthService
import com.example.step5app.domain.repositories.AuthRepository
import javax.inject.Inject
import com.example.step5app.data.model.ConfirmOtpRequest
import com.example.step5app.data.model.ConfirmOtpResponse
import com.example.step5app.data.model.ProfileResponse
import com.example.step5app.data.model.RequestForgetPasswordRequest
import com.example.step5app.data.model.SignInRequest
import com.example.step5app.data.model.SignInResponse
import com.example.step5app.data.model.UpdateProfileRequest
import com.example.step5app.data.model.VerifyForgetPasswordRequest

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,  // API service
    private val userPreferences: UserPreferences,
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Result<SignInResponse> {
        return try {
            val response = authService.login(SignInRequest(email, password), "en")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
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

    override suspend fun getProfile(): Result<ProfileResponse> {
        val token = userPreferences.getAccessTokenOnce()
        return try {
            val bearer = "Bearer $token"
            val response = authService.getProfile(bearer)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(request: UpdateProfileRequest, token: String): Result<ProfileResponse> {
        return try {
            val bearer = "Bearer $token"
            val response = authService.updateProfile(token = bearer, body = request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(request: ChangePasswordRequest): Result<Unit> {
        return try {
            val response = authService.changePassword(body = request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun requestForgetPassword(request: RequestForgetPasswordRequest): Result<Unit> {
        return try {
            val response = authService.requestForgetPassword(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
//                val errorMessage = errorBody?.let { JSONObject(it).optString("message") }
                Result.failure(Exception(errorBody ?: "Failed to request password reset"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verifyForgetPassword(request: VerifyForgetPasswordRequest): Result<Unit> {
        return try {
            val response = authService.verifyForgetPassword(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
//                val errorMessage = errorBody?.let { JSONObject(it).optString("message") }
                Result.failure(Exception(errorBody ?: "Failed to verify code"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changeForgetPassword(request: ChangeForgetPasswordRequest): Result<Unit> {
        return try {
            val response = authService.changeForgetPassword(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
//                val errorMessage = errorBody?.let { JSONObject(it).optString("message") }
                Result.failure(Exception(errorBody ?: "Failed to change password"))
            }
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