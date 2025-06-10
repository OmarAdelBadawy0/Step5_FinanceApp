package com.example.step5app.presentation.feed

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
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
            Post("# TITLE 4", "DESCRIPTION 4", "May 3, 2023"),
            Post("# TITLE 5", "DESCRIPTION 5", "May 2, 2023"),
            Post("# TITLE 1", "DESCRIPTION 1", "May 6, 2023"),
            Post("# TITLE 2", "DESCRIPTION 2", "May 5, 2023"),
            Post("# TITLE 3", "DESCRIPTION 3", "May 4, 2023")
        )
    }

    fun setLocalizedStrings(filterOptions: List<String>, categories: List<String>, defaultFilter: String) {
        _feedUiState.update { currentState ->
            currentState.copy(
                filterOptions = filterOptions,
                categories = categories,
                selectedFilter = defaultFilter
            )
        }
    }
}