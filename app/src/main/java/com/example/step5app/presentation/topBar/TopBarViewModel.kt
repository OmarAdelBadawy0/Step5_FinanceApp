package com.example.step5app.presentation.topBar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TopBarViewModel : ViewModel() {
    private val _searchText = mutableStateOf("")
    val searchText = _searchText

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    fun clearSearch() {
        _searchText.value = ""
    }
}