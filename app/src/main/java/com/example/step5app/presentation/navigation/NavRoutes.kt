package com.example.step5app.presentation.navigation

sealed class Screen(val route: String) {
    object AuthR : Screen("auth_root")  // This will contain both sign-in/sign-up
    object Auth : Screen("auth")
    object MainApp : Screen("main_app")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up")
    object ForgotPassword : Screen("forgot_password")
    object Feed : Screen("feed")  // Renamed from Home to Feed for clarity
    object FeedDetails : Screen("feed_details/{feedId}") {
        fun createRoute(feedId: Int): String {
            return "feed_details/$feedId"
        }
    }
    object Settings : Screen("settings")
    object Courses : Screen("courses")
    object Network : Screen("network")
    object Profile : Screen("profile?tabIndex={tabIndex}") {
        fun createRoute(tabIndex: Int) = "profile?tabIndex=$tabIndex"
    }
}