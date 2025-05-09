package com.example.step5app.presentation.feed
import com.example.step5app.domain.model.Post

data class FeedUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val selectedCategory: String = "",
    val selectedFilter: String = "Filter",
    val isFilterDropdownExpanded: Boolean = false,
    val errorMessage: String? = null,
    val filterOptions: List<String> = listOf(
        "Today",
        "This Week",
        "Last Week",
        "This Month"
    ),
    val categories: List<String> = listOf(
        "Category 1",
        "Category 2",
        "Category 3",
        "Category 4"
    )
)