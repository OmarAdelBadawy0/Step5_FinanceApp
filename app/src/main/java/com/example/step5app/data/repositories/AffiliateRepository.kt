package com.example.step5app.data.repositories

import com.example.step5app.data.local.UserPreferences
import com.example.step5app.data.model.AffiliateUserInfoResponse
import com.example.step5app.data.model.InviteCodeResponse
import com.example.step5app.data.remote.AffiliateService
import javax.inject.Inject

class AffiliateRepository @Inject constructor(
    private val affiliateService: AffiliateService,
    private val userPreferences: UserPreferences
) {
    suspend fun getUserAffiliateInfo(): Result<AffiliateUserInfoResponse> {
        val token = userPreferences.getAccessTokenOnce()
        return try {
            val response = affiliateService.getUserAffiliateInfo(token = "Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getInviteCode(language: String = "en"): InviteCodeResponse? {
        val token = userPreferences.getAccessTokenOnce()
        val response = affiliateService.getInviteCode(language, token =  "Bearer $token")
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }
}