package com.example.step5app.presentation.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")  // This will contain both sign-in/sign-up
    object Feed : Screen("feed")  // Renamed from Home to Feed for clarity
}