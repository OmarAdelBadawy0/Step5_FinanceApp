package com.example.step5app.presentation.feed
import com.example.step5app.domain.model.Category
import com.example.step5app.domain.model.Post

data class FeedUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val selectedCategory: Category? = null,
    val searchedQuery: String? = null,
    val selectedFilter: String = "",
    val isFilterDropdownExpanded: Boolean = false,
    val errorMessage: String? = null,
    val filterOptions: List<String> = emptyList(),
    val categories: List<Category> = emptyList(),
    val currentPage: Int = 1,
    val isLoadingMorePosts: Boolean = false,
    val hasMorePosts: Boolean = true
)