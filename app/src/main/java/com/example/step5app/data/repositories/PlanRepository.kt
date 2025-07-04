package com.example.step5app.data.repositories

import com.example.step5app.data.remote.PlanService
import com.example.step5app.domain.model.Plan
import javax.inject.Inject

class PlanRepository @Inject constructor(
    private val planService: PlanService
) {
    suspend fun fetchPlans(): List<Plan> {
        val response = planService.getPlans()
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Failed to fetch plans")
        }
    }
}