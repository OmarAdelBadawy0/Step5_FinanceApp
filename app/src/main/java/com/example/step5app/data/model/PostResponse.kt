package com.example.step5app.data.model

import com.example.step5app.domain.model.Post

data class PostResponse(
    val message: String,
    val data: List<Post>,
    val pagination: Pagination
)



data class Pagination(
    val currentPage: Int,
    val totalPages: Int,
    val limit: Int
)