package com.example.step5app.presentation.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.step5app.R
import com.example.step5app.presentation.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    viewModel: BottomBarViewModel = hiltViewModel(),
    navController: NavController
) {
    val selectedTab = viewModel.selectedTab.intValue
    val tabs = listOf(
        stringResource(R.string.recommendations),
        stringResource(R.string.courses),
        stringResource(R.string.make_money),
        stringResource(R.string.profile))

    val isDarkTheme = isSystemInDarkTheme()

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        tabs.forEachIndexed { index, title ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = {
                    viewModel.selectTab(index)

                    when (index) {
                        0 -> navController.navigate(Screen.Feed.route)
                        1 -> navController.navigate(Screen.Courses.route)
                        2 -> navController.navigate(Screen.Network.route)
                        3 -> navController.navigate(Screen.Profile.route)
                        else -> navController.navigate(Screen.Feed.route)
                    }
                          },
                icon = {
                    when (index) {
                        0 -> Icon(
                            painter = painterResource(R.drawable.feed),
                            contentDescription = title,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                        1 -> Icon(
                            painter = painterResource(R.drawable.book),
                            contentDescription = title,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        2 -> Icon(
                            painter = painterResource(R.drawable.people),
                            contentDescription = title,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        3 -> Icon(
                            painter = painterResource(R.drawable.person),
                            contentDescription = title,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        else -> Icon(Icons.Default.Search, title)
                    }
                },
                label = {
                    Text(
                        title,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .drawWithContent {
                        drawContent()
                        if (selectedTab == index) {
                            drawLine(
                                color = if (isDarkTheme) Color.White else Color.Black,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = 5.dp.toPx(),
                            )
                        }
                    }
                    .background(
                        if (selectedTab == index) Color.Gray.copy(alpha = 0.2f)
                        else Color.Transparent,
                        shape = RectangleShape
                    )
            )
        }
    }
}