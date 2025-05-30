package com.example.step5app.presentation.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.step5app.presentation.bottomBar.BottomBar
import com.example.step5app.presentation.myCourses.MyCoursesScreen
import com.example.step5app.presentation.topBar.TopBar

@Composable
fun CoursesScreen(
    navController: NavController
){
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        topBar = { TopBar() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            MyCoursesScreen()
        }
    }
}