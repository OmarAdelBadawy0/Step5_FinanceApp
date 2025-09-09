package com.example.step5app.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.data.local.UserPreferences
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
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _feedUiState = MutableStateFlow(FeedUiState())
    val feedUiState: StateFlow<FeedUiState> = _feedUiState.asStateFlow()

    init {
        loadPosts()
        loadCategories()
    }


    internal fun loadPosts() {
        if (feedUiState.value.isLoadingMorePosts || !feedUiState.value.hasMorePosts) return
        _feedUiState.update { currentState ->
            currentState.copy(isLoadingMorePosts = true)
        }

        viewModelScope.launch {
            try {
                // get the posts and append them to the current posts list
                val result = feedRepository.fetchPosts(
                    page = feedUiState.value.currentPage,
                    categoryId = feedUiState.value.selectedCategory?.id,
                    search = feedUiState.value.searchedQuery
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
                if (e.message?.contains("401") == true) userPreferences.clearAccessToken()
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
        if (category == _feedUiState.value.selectedCategory) {
            _feedUiState.update { currentState ->
                currentState.copy(
                    selectedCategory = null,
                    searchedQuery = null,
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
                selectedCategory = category,
                searchedQuery = null,
                posts = emptyList(),
                currentPage = 1,
                hasMorePosts = true
            )
        }
        loadPosts()
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

    fun searchPosts(query: String){
        _feedUiState.update { currentState ->
            currentState.copy(
                searchedQuery = query,
                posts = emptyList(),
                currentPage = 1,
                hasMorePosts = true
            )
        }
        loadPosts()
    }



    fun setLocalizedStrings(filterOptions: List<String>, defaultFilter: String) {
        _feedUiState.update { currentState ->
            currentState.copy(
                filterOptions = filterOptions,
                selectedFilter = defaultFilter
            )
        }
    }

    fun getImageUrl(imageId: Int): String{
        return feedRepository.getBaseImagesUrl() + imageId
    }
}