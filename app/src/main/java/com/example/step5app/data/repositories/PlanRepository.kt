package com.example.step5app.data.repositories

import android.util.Log
import com.example.step5app.data.local.SubscriptionRequest
import com.example.step5app.data.local.SubscriptionResponse
import com.example.step5app.data.local.UserPreferences
import com.example.step5app.data.remote.PlanService
import com.example.step5app.domain.model.Plan
import javax.inject.Inject

class PlanRepository @Inject constructor(
    private val planService: PlanService,
    private val userPreferences: UserPreferences
) {
    suspend fun fetchPlans(): List<Plan> {
        val response = planService.getPlans()
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Failed to fetch plans")
        }
    }

    suspend fun makeSubscription(request: SubscriptionRequest): SubscriptionResponse {
        val token = userPreferences.getAccessTokenOnce()
        val response = planService.makeSubscription(token = "Bearer $token", request = request)
        Log.d("SubscriptionResponse", response.message())
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Failed to make subscription")
        } else {
            throw Exception(response.code().toString())
        }
    }

}