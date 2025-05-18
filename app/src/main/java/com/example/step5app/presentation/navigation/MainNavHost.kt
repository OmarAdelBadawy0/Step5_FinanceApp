package com.example.step5app.presentation.navigation

import FeedScreen
import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.step5app.presentation.auth.AuthScreen
import com.example.step5app.presentation.feed.FeedViewModel
import com.example.step5app.presentation.settings.SettingsViewModel

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route
    ) {
        // Auth Screen (contains both sign-in/sign-up)
        composable(Screen.Auth.route) {
            AuthScreen(
                onSignInSuccess = {
                    navController.navigate(Screen.Feed.route) {
                        // Clear back stack so user can't go back to auth
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        // Feed Screen
        composable(Screen.Feed.route) {
            val viewModel = hiltViewModel<FeedViewModel>()
            FeedScreen(
                viewModel = viewModel,
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Settings.route) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}