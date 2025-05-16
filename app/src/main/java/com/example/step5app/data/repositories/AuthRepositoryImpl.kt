package com.example.step5app.data.repositories

import android.content.SharedPreferences
import com.example.step5app.data.model.SignInRequest
import com.example.step5app.data.model.SignUpRequest
import com.example.step5app.data.remote.AuthService
import com.example.step5app.domain.repositories.AuthRepository
import javax.inject.Inject
import androidx.core.content.edit

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,  // Your API service
    private val preferences: SharedPreferences // For local storage
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
        return Result.success(Unit)
//        return try {
//            val response = authService.signUp(SignUpRequest(firstName, lastName, email, password))
//            if (response.success) {
//                Result.success(Unit)
//            } else {
//                Result.failure(Exception(response.error ?: "Sign-up failed"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override fun isUserLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }
}