package com.example.step5app.data.remote

import com.example.step5app.data.local.SubscriptionRequest
import com.example.step5app.data.local.SubscriptionResponse
import com.example.step5app.data.model.PlanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PlanService {
    @GET("/plans")
    suspend fun getPlans(
        @Header("Accept-Language") lang: String = "en"
    ): Response<PlanResponse>

    @POST("subscriptions")
    suspend fun makeSubscription(
        @Header("Accept-Language") lang: String = "en",
        @Header("Authorization") token: String,
        @Body request: SubscriptionRequest
    ): Response<SubscriptionResponse>
}