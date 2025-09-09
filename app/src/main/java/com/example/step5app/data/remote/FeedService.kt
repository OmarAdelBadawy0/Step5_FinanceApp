package com.example.step5app.data.remote

import com.example.step5app.data.model.CategoriesResponse
import com.example.step5app.data.model.FeedDetailsResponse
import com.example.step5app.data.model.PostResponse
import com.example.step5app.domain.model.Category
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedService {
    @GET("/categories")
    suspend fun getCategories(
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): CategoriesResponse<List<Category>>

    @GET("/categories/{id}")
    suspend fun getCategories(
        @Path("id") categoryId: Int,
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String,
    ): CategoriesResponse<Category>

    @GET("/articles")
    suspend fun getPosts(
        @Query("categoryId") categoryId: Int? = null,
        @Query("search") search: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 5,
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String,
    ): PostResponse

    @GET("articles/{id}")
    suspend fun getFeedDetails(
        @Path("id") id: Int,
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String,
    ): FeedDetailsResponse
}