package com.example.step5app.presentation.navigation

import FeedScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.step5app.presentation.auth.AuthScreen
import com.example.step5app.presentation.feed.FeedViewModel

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onAuthComplete: () -> Unit
) {
    navigation(
        startDestination = Screen.Auth.route,  // Matches your Screen.Auth.route
        route = "auth_root"         // Unique identifier for this graph
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                onSignInSuccess = {
                    onAuthComplete()  // Will trigger navigation to feed
                }
            )
        }
    }
}

fun NavGraphBuilder.mainGraph() {
    composable(Screen.Feed.route) {  // Matches your Screen.Feed.route
        val viewModel = hiltViewModel<FeedViewModel>()
        FeedScreen(viewModel = viewModel)
    }
}