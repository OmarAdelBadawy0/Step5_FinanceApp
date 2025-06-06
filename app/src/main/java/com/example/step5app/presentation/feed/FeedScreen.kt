import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.step5app.R
import com.example.step5app.presentation.bottomBar.BottomBar
import com.example.step5app.presentation.feed.FeedViewModel
import com.example.step5app.presentation.topBar.TopBar


@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onSettingsClick: () -> Unit,
    navController: NavController
) {
    val uiState by viewModel.feedUiState.collectAsState()

    // Filter Options
    val filterOptions = listOf(
        stringResource(R.string.today),
        stringResource(R.string.this_week),
        stringResource(R.string.last_week),
        stringResource(R.string.this_month)
    )
    val defaultFilter = stringResource(R.string.filter)

    // Categories options
    val categories = listOf(
        stringResource(R.string.category1),
        stringResource(R.string.category2),
        stringResource(R.string.category3),
        stringResource(R.string.category4)
    )

    // This ensures it's only called once (set the filter and categories)
    LaunchedEffect(Unit) {
        viewModel.setLocalizedStrings(filterOptions, categories, defaultFilter)
    }

    Scaffold(
        topBar = { TopBar(onSettingsClick = onSettingsClick, withSearch = true) },
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.errorMessage ?: "Error loading posts",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                else -> {
                    // Filter Row
                    Box {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(vertical = 6.dp)
                                .clickable { viewModel.toggleFilterDropdown() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.filter),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(22.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = uiState.selectedFilter,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                painter = if (uiState.isFilterDropdownExpanded) painterResource(R.drawable.arrow_right_short)
                                else painterResource(R.drawable.arrow_down_short),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = uiState.isFilterDropdownExpanded,
                            onDismissRequest = { viewModel.toggleFilterDropdown() }
                        ) {
                            uiState.filterOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        viewModel.selectFilter(option)
                                        viewModel.toggleFilterDropdown()
                                    }
                                )
                            }
                        }
                    }

                    // Category Chips
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(vertical = 8.dp)
                    ) {
                        uiState.categories.forEach { category ->
                            FilterChip(
                                selected = category == uiState.selectedCategory,
                                onClick = { viewModel.selectCategory(category) },
                                label = {
                                    Text(
                                        text = category,
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        color = if (category == uiState.selectedCategory) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.padding(0.dp)
                                    )
                                },
                                modifier = Modifier
                                    .height(40.dp)
                                    .minimumInteractiveComponentSize()
                                    .padding(end = 8.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                    containerColor = Color.Transparent
                                ),
                                border = if (category == uiState.selectedCategory) BorderStroke(1.dp, Color.Transparent)
                                else BorderStroke((1.5).dp, MaterialTheme.colorScheme.tertiary)
                            )
                        }
                    }

                    // List of Items
                    LazyColumn {
                        items(uiState.posts.reversed()) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(2.dp),
                                shape = RoundedCornerShape(0.dp),
                                onClick = {}
                            ) {
                                Column(
                                    Modifier.background(MaterialTheme.colorScheme.surface)
                                ) {
                                    // Placeholder for Image
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp)
                                            .background(Color.Gray)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = item.title,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = item.description,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 12.dp, bottom = 4.dp),
                                        contentAlignment = Alignment.BottomEnd
                                    ) {
                                        Text(
                                            text = item.date,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FeedScreenPreview() {
//    FeedScreen(
//        viewModel = viewModel(),
//        onSettingsClick = {},
//        navController = NavController(LocalContext.current)
//    )
//}