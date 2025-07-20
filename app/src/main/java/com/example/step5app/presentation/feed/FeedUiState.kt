package com.example.step5app.presentation.feed
import com.example.step5app.domain.model.Category
import com.example.step5app.domain.model.Post

data class FeedUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val selectedCategory: String = "",
    val selectedFilter: String = "",
    val isFilterDropdownExpanded: Boolean = false,
    val errorMessage: String? = null,
    val filterOptions: List<String> = emptyList(),
    val categories: List<Category> = emptyList()
)