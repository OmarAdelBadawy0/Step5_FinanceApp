package com.example.step5app.data.remote

import com.example.step5app.data.model.CategoriesResponse
import com.example.step5app.domain.model.Category
import retrofit2.http.GET
import retrofit2.http.Header

interface FeedService {
    @GET("/categories")
    suspend fun getCategories(
        @Header("Accept-Language") language: String = "en",
        @Header("Authorization") token: String
    ): CategoriesResponse<List<Category>>
}