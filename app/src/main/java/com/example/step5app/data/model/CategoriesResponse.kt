package com.example.step5app.data.model

data class CategoriesResponse<T>(
    val message: String,
    val data: T,
    val pagination: Any? = null
)
