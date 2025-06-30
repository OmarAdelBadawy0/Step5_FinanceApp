package com.example.step5app.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.step5app.data.local.UserPreferences

@Composable
fun MainNavHost(
    userPreferences: UserPreferences = UserPreferences(context = LocalContext.current)
) {
    val navController = rememberNavController()

    val token = userPreferences.accessToken.collectAsState(initial = null).value

    // Navigate to Auth screen if token is null or to mainApp if token exist
    LaunchedEffect(token) {
        if (token == null) {
            navController.navigate(Screen.Auth.route) {
                popUpTo(Screen.MainApp.route) { inclusive = true }
            }
        }else{
            navController.navigate(Screen.MainApp.route) {
                popUpTo(Screen.Auth.route) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.AuthR.route,
    ) {


        authGraph(navController, onAuthComplete = { navController.navigate(Screen.MainApp.route) })
        mainGraph(navController)


    }
}