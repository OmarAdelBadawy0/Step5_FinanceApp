package com.example.step5app.domain.repositories

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<Unit>

    suspend fun signOut()
    fun isUserLoggedIn(): Boolean
}