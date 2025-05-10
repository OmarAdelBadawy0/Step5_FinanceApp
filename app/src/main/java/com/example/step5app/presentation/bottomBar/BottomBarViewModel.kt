package com.example.step5app.presentation.bottomBar

import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class BottomBarViewModel @Inject constructor() : ViewModel() {
    private val _selectedTab = mutableIntStateOf(0)
    val selectedTab: IntState = _selectedTab

    fun selectTab(index: Int) {
        _selectedTab.intValue = index
    }
}
