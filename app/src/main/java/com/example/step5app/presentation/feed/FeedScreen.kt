import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

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

    // This ensures it's only called once (set the filter and categories)
    LaunchedEffect(Unit) {
        viewModel.setLocalizedStrings(filterOptions, defaultFilter)
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // handle the scroll to top button
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }


    // handle the pagination when the user scrolls to the end of the list -1
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleIndex == totalItems - 1) {
                    viewModel.loadPosts()
                }
            }
    }

    Scaffold(
        topBar = { TopBar(onSettingsClick = onSettingsClick, withSearch = true) },
        bottomBar = { BottomBar(navController = navController) },
        floatingActionButton = {
            if (showScrollToTop) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Icon(
                        painterResource(R.drawable.arrow_up_short),
                        contentDescription = stringResource(R.string.scroll_to_top),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.scale(2.2f)
                    )
                }
            }
        }
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
                                selected = category.name == uiState.selectedCategory,
                                onClick = { viewModel.selectCategory(category) },
                                label = {
                                    Text(
                                        text = category.name,
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        color = if (category.name == uiState.selectedCategory) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.padding(0.dp)
                                    )
                                },
                                modifier = Modifier
                                    .height(35.dp)
                                    .minimumInteractiveComponentSize()
                                    .padding(end = 8.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                    containerColor = Color.Transparent
                                ),
                                border = if (category.name == uiState.selectedCategory) BorderStroke(1.dp, Color.Transparent)
                                else BorderStroke((1.5).dp, MaterialTheme.colorScheme.tertiary)
                            )
                        }
                    }

                    LazyColumn(state = listState) {
                        items(uiState.posts) { item ->
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
                                        // Parse the date and format it
                                        val parsedDate = OffsetDateTime.parse(item.updatedAt)
                                        val formatter = DateTimeFormatter.ofPattern("HH:mm. dd.MM.yy")
                                        Text(
                                            text = parsedDate.format(formatter),
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                        if (uiState.hasMorePosts) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
