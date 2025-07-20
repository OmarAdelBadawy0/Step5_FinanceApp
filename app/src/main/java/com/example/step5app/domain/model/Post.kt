package com.example.step5app.domain.model

data class Post(
    val id: Int,
    val title: String,
    val description: String,
    val updatedAt: String,
    val createdAt: String,
    val Category: CategoryRef,
    val Thumbnail: Thumbnail,
    val isLocked: Boolean
)


data class CategoryRef(
    val id: Int,
    val planId: Int
)

data class Thumbnail(
    val id: Int,
    val mime: String,
    val name: String
)