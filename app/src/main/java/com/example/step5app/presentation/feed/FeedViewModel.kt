package com.example.step5app.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.data.repositories.FeedRepository
import com.example.step5app.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
) : ViewModel() {
    private val _feedUiState = MutableStateFlow(FeedUiState())
    val feedUiState: StateFlow<FeedUiState> = _feedUiState.asStateFlow()

    init {
        loadPosts()
        loadCategories()
    }


    internal fun loadPosts(categoryId: Int? = null, search: String? = null) {
        if (feedUiState.value.isLoadingMorePosts || !feedUiState.value.hasMorePosts) return
        _feedUiState.update { currentState ->
            currentState.copy(isLoadingMorePosts = true)
        }

        viewModelScope.launch {
            try {
                // get the posts and append them to the current posts list
                val result = feedRepository.fetchPosts(
                    page = feedUiState.value.currentPage,
                    categoryId = categoryId,
                    search = search
                )

                _feedUiState.update { currentState ->
                    currentState.copy(
                        posts = currentState.posts + result.data,
                        isLoading = false
                    )
                }

                // Update the current page and check if there are more pages
                _feedUiState.update { currentState ->
                    currentState.copy(
                        currentPage = result.pagination.currentPage + 1,
                        hasMorePosts = result.pagination.currentPage < result.pagination.totalPages
                    )
                }
            } catch (e: Exception) {
                _feedUiState.update { currentState ->
                    currentState.copy(errorMessage = e.message)
                }
            } finally {
                _feedUiState.update { currentState -> currentState.copy(isLoadingMorePosts = false) }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = feedRepository.fetchCategories()
                _feedUiState.update { currentState ->
                    currentState.copy(
                        categories = categories
                    )
                }
            } catch (e: Exception) {
                _feedUiState.update { it.copy(errorMessage = "Failed to load categories" + e.localizedMessage) }
            }
        }
    }

    fun selectCategory(category: Category) {

        // If the category is already selected, clear the selection and load all posts
        if (category.name == _feedUiState.value.selectedCategory) {
            _feedUiState.update { currentState ->
                currentState.copy(
                    selectedCategory = "",
                    posts = emptyList(),
                    currentPage = 1,
                    hasMorePosts = true
                )
            }
            loadPosts()
            return
        }

        // if select new category, clear the posts and load the new category posts
        _feedUiState.update { currentState ->
            currentState.copy(
                selectedCategory = category.name,
                posts = emptyList(),
                currentPage = 1,
                hasMorePosts = true
            )
        }
        loadPosts(categoryId = category.id)
    }

    fun selectFilter(option: String) {
        _feedUiState.update { currentState ->
            currentState.copy(
                selectedFilter = option
            )
        }
    }

    fun toggleFilterDropdown() {
        _feedUiState.update { currentState ->
            currentState.copy(
                isFilterDropdownExpanded = !currentState.isFilterDropdownExpanded
            )
        }
    }

//    private fun generateSamplePosts(): List<Post> {
//        return listOf(
//            Post("# TITLE 4", "DESCRIPTION 4", "May 3, 2023"),
//            Post("# TITLE 5", "DESCRIPTION 5", "May 2, 2023"),
//            Post("# TITLE 1", "DESCRIPTION 1", "May 6, 2023"),
//            Post("# TITLE 2", "DESCRIPTION 2", "May 5, 2023"),
//            Post("# TITLE 3", "DESCRIPTION 3", "May 4, 2023")
//        )
//    }

    fun setLocalizedStrings(filterOptions: List<String>, defaultFilter: String) {
        _feedUiState.update { currentState ->
            currentState.copy(
                filterOptions = filterOptions,
                selectedFilter = defaultFilter
            )
        }
    }
}