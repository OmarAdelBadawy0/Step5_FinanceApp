package com.example.step5app.presentation.navigation

import FeedScreen
import SettingsScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.step5app.presentation.auth.AuthScreen
import com.example.step5app.presentation.feed.FeedViewModel
import com.example.step5app.presentation.profile.ProfileScreen
import com.example.step5app.presentation.profile.ProfileViewModel
import com.example.step5app.presentation.settings.SettingsViewModel

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = Screen.Feed.route,
        route = Screen.MainApp.route
    ) {
        composable(Screen.Feed.route) {
            val viewModel = hiltViewModel<FeedViewModel>()
            FeedScreen(
                viewModel = viewModel,
                onSettingsClick = { navController.navigate(Screen.Settings.route) },
                navController = navController
            )
        }

        composable(Screen.Courses.route) {
            // Add your Courses screen implementation
        }

        composable(Screen.Network.route) {
            // Add your Network screen implementation
        }

        composable(Screen.Profile.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                viewModel = viewModel,
                onBackClick = { navController.navigate(Screen.Feed.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) },
                navController = navController
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

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onAuthComplete: () -> Unit
) {
    navigation(
        startDestination = Screen.Auth.route,
        route = Screen.AuthR.route
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                onSignInSuccess = {
                   navController.navigate(Screen.MainApp.route){
                       popUpTo(Screen.Auth.route){inclusive = true}
                       launchSingleTop = true
                   }
                }
            )
        }
    }
}