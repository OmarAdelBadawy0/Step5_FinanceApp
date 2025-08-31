package com.example.step5app.data.remote

import com.example.step5app.data.model.AffiliateUserInfoResponse
import com.example.step5app.data.model.InviteCodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AffiliateService {

    @GET("users")
    suspend fun getUserAffiliateInfo(
        @Query("userId") userId: String? = "cmevhxw2h0001ob303rgt4x1s",
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): Response<AffiliateUserInfoResponse>

    @GET("connections/get-code")
    suspend fun getInviteCode(
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): Response<InviteCodeResponse>

}