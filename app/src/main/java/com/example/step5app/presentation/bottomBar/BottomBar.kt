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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.step5app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    viewModel: BottomBarViewModel = viewModel()
) {
    val selectedTab = viewModel.selectedTab.intValue
    val tabs = listOf(
        stringResource(R.string.feed),
        stringResource(R.string.courses),
        stringResource(R.string.network),
        stringResource(R.string.profile))

    val isDarkTheme = isSystemInDarkTheme()

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        tabs.forEachIndexed { index, title ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { viewModel.selectTab(index) },
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
                label = { Text(title, color = MaterialTheme.colorScheme.onSurface) },
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