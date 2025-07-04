package com.example.step5app.data.remote

import com.example.step5app.data.model.PlanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PlanService {
    @GET("/plans")
    suspend fun getPlans(
        @Header("Accept-Language") lang: String = "en"
    ): Response<PlanResponse>
}