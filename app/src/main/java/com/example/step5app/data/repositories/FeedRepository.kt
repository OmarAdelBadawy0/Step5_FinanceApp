package com.example.step5app.data.repositories

import com.example.step5app.data.remote.FeedService
import com.example.step5app.domain.model.Category
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val feedServiceApi: FeedService
) {
    suspend fun fetchCategories(token: String): List<Category> {
        return feedServiceApi.getCategories(token = "Bearer $token").data
    }
}