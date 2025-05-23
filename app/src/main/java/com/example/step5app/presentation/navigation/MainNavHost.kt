package com.example.step5app.presentation.navigation

import FeedScreen
import SettingsScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.step5app.presentation.auth.AuthScreen
import com.example.step5app.presentation.feed.FeedViewModel
import com.example.step5app.presentation.settings.SettingsViewModel

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

//        authGraph(navController) {
//            navController.navigate("main_root") {
//                popUpTo(navController.graph.findStartDestination().id) {
//                    saveState = true
//                }
//                launchSingleTop = true
//                restoreState = true
//            }
//        }
//
//        mainGraph(navController)
    }
}