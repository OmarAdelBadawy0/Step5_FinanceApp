package com.example.step5app.presentation.feedDetails

import com.example.step5app.data.local.UiText
import com.example.step5app.domain.model.Category
import com.example.step5app.domain.model.Thumbnail

data class FeedDetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val id: Int = 0,
    val title: String = "",
    val date: String = "",
    val description: String = "",
    val content: String = "",
    val imageUrl: Thumbnail? = null,
    val category: Category? = null,
)
