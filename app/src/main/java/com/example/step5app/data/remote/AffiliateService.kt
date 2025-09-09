package com.example.step5app.data.remote

import com.example.step5app.data.model.ApiResponse
import com.example.step5app.data.model.ConnectionsResponse
import com.example.step5app.data.model.InviteCodeRequest
import com.example.step5app.data.model.InviteCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AffiliateService {
    @GET("connections/get-code")
    suspend fun getInviteCode(
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): Response<InviteCodeResponse>

    @GET("connections")
    suspend fun getConnections(
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): Response<ConnectionsResponse>

    @POST("connections")
    suspend fun addConnection(
        @Body body: InviteCodeRequest,
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): Response<ApiResponse>

    @DELETE("connections/{childId}")
    suspend fun deleteConnection(
        @Path("childId") childId: String,
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): Response<Unit>
}