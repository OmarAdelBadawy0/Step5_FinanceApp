package com.example.step5app.data.repositories

import com.example.step5app.data.local.UserPreferences
import com.example.step5app.data.model.FeedDataDto
import com.example.step5app.data.model.PostResponse
import com.example.step5app.data.remote.FeedService
import com.example.step5app.domain.model.Category
import java.util.Locale
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val feedServiceApi: FeedService,
    private val userPreferences: UserPreferences
) {

    private val BASE_URL_IMAGES = "https://api.step5th.com/files/images/"

    suspend fun fetchCategories(): List<Category> {
        val locale = Locale.getDefault().language // "ar" or "en"
        val token = userPreferences.getAccessTokenOnce() ?: ""

        return feedServiceApi.getCategories(language = locale ,token = "Bearer $token").data
    }

    suspend fun getCategory(categoryId: Int): Category{
        val locale = Locale.getDefault().language // "ar" or "en"
        val token = userPreferences.getAccessTokenOnce() ?: ""

        return feedServiceApi.getCategories(categoryId = categoryId, language = locale ,token = "Bearer $token").data
    }

    suspend fun fetchPosts(
        categoryId: Int? = null,
        search: String? = null,
        page: Int = 1,
        limit: Int = 5,
        language: String = Locale.getDefault().language
    ): PostResponse {
        val token = userPreferences.getAccessTokenOnce() ?: ""
        return feedServiceApi.getPosts(categoryId, search, page, limit, language, "Bearer $token")
    }

    fun getBaseImagesUrl(): String = BASE_URL_IMAGES

    suspend fun getFeedDetails(feedId: Int): FeedDataDto {
        val token = userPreferences.getAccessTokenOnce() ?: ""
        val locale = Locale.getDefault().language
        val response = feedServiceApi.getFeedDetails(feedId, language = locale, "Bearer $token")
        return response.data
    }
}