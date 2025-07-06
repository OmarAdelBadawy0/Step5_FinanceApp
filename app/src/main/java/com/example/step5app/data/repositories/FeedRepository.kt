package com.example.step5app.data.repositories

import com.example.step5app.data.local.UserPreferences
import com.example.step5app.data.remote.FeedService
import com.example.step5app.domain.model.Category
import java.util.Locale
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val feedServiceApi: FeedService,
    private val userPreferences: UserPreferences
) {
    suspend fun fetchCategories(): List<Category> {
        val locale = Locale.getDefault().language // "ar" or "en"
        val token = userPreferences.getAccessTokenOnce() ?: ""

        return feedServiceApi.getCategories(language = locale ,token = "Bearer $token").data
    }
}