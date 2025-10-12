package com.example.step5app.data.repositories

import com.example.step5app.data.local.UserPreferences
import com.example.step5app.data.model.ApiResponse
import com.example.step5app.data.model.CashoutRequest
import com.example.step5app.data.model.CashoutResponse
import com.example.step5app.data.model.ConnectionData
import com.example.step5app.data.model.InviteCodeRequest
import com.example.step5app.data.model.InviteCodeResponse
import com.example.step5app.data.remote.AffiliateService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AffiliateRepository @Inject constructor(
    private val affiliateService: AffiliateService,
    private val userPreferences: UserPreferences
) {
    suspend fun getInviteCode(language: String = "en"): InviteCodeResponse? {
        val token = userPreferences.getAccessTokenOnce()
        val response = affiliateService.getInviteCode(language, token =  "Bearer $token")
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

    suspend fun getConnections(language: String = "en"): Result<List<ConnectionData>> {
        val token = userPreferences.getAccessTokenOnce()
        return try {
            val response = affiliateService.getConnections(language, token = "Bearer $token")
            if (response.isSuccessful) {
                val body = response.body()
                Result.success(body?.data ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addConnection(inviteCode: String, language: String = "en"): Result<ApiResponse> {
        return try {
            val token = userPreferences.getAccessTokenOnce()
            val request = InviteCodeRequest(inviteCode)

            val response = affiliateService.addConnection(
                body = request,
                language = language,
                token = "Bearer $token"
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: response.message()
                Result.failure(Exception("Error ${response.code()} - $errorMsg"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    suspend fun deleteConnection(childId: String, language: String = "en"): Result<Unit> {
        val token = userPreferences.getAccessTokenOnce()
        return try {
            val response = affiliateService.deleteConnection(childId, language, token = "Bearer $token")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createCashout(request: CashoutRequest): Result<CashoutResponse> {
        val token = userPreferences.getAccessTokenOnce()

        return withContext(Dispatchers.IO) {
            try {
                val response = affiliateService.createCashout(request = request, token = "Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let { Result.success(it) }
                        ?: Result.failure(Exception("Empty response"))
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}