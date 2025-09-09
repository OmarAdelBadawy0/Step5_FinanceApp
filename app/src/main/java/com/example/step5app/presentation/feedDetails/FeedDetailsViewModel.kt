package com.example.step5app.presentation.feedDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.step5app.data.local.UiText
import com.example.step5app.data.repositories.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedDetailsViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedDetailsUiState())
    val uiState: StateFlow<FeedDetailsUiState> = _uiState.asStateFlow()

    fun loadFeedDetails(feedId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val feed = feedRepository.getFeedDetails(feedId)
                val category = feedRepository.getCategory(feed.Category?.id ?: 0)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        title = feed.title,
                        date = feed.updatedAt,
                        description = feed.description,
                        content = feed.content,
                        imageUrl = feed.Thumbnail,
                        category = category,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.DynamicString(e.message ?: "Failed to load feed details")
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }


    // helper extension like in your subscription VM
    private fun MutableStateFlow<FeedDetailsUiState>.update(
        transform: (FeedDetailsUiState) -> FeedDetailsUiState
    ) {
        this.value = transform(this.value)
    }
}
