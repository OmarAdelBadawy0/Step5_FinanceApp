package com.example.step5app.presentation.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.domain.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val _feedUiState = MutableStateFlow(FeedUiState())
    val feedUiState: StateFlow<FeedUiState> = _feedUiState.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _feedUiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    posts = emptyList(),
                    errorMessage = null
                )
            }

            try {
                // Simulate network/database call
                kotlinx.coroutines.delay(500)

                _feedUiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        posts = generateSamplePosts()
                    )
                }
            } catch (e: Exception) {
                _feedUiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = "Failed to load posts: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    fun selectCategory(category: String) {
        _feedUiState.update { currentState ->
            currentState.copy(
                selectedCategory = if (currentState.selectedCategory == category) "" else category
            )
        }
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

    private fun generateSamplePosts(): List<Post> {
        return listOf(
            Post("# TITLE 1", "DESCRIPTION 1", "May 6, 2023"),
            Post("# TITLE 2", "DESCRIPTION 2", "May 5, 2023"),
            Post("# TITLE 3", "DESCRIPTION 3", "May 4, 2023")
        )
    }
}