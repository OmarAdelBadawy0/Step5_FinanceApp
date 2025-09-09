package com.example.step5app.data.model

import com.example.step5app.domain.model.CategoryRef
import com.example.step5app.domain.model.Thumbnail

data class FeedDetailsResponse(
    val message: String,
    val data: FeedDataDto
)

data class FeedDataDto(
    val id: Int,
    val title: String,
    val description: String,
    val content: String,
    val updatedAt: String,
    val createdAt: String,
    val Category: CategoryRef?,
    val Thumbnail: Thumbnail?
)
