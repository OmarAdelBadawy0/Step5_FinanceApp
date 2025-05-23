package com.example.step5app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.step5app.presentation.settings.SettingsViewModel
import com.example.step5app.ui.theme.Step5AppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import com.example.step5app.presentation.navigation.MainNavHost
import com.example.step5app.presentation.profile.ProfileScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            Step5AppTheme(
                selectedTheme = uiState.themePreference.lowercase()
            ) {
                MainNavHost()
            }
        }
    }
}



