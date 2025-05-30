package com.example.step5app.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavHost(
    innerPadding: PaddingValues = PaddingValues()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.AuthR.route,
    ) {


        authGraph(navController, onAuthComplete = {})
        mainGraph(navController)


    }
}