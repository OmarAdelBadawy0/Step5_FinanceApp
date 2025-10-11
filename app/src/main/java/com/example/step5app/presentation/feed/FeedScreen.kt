import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.step5app.R
import com.example.step5app.domain.model.Category
import com.example.step5app.domain.model.Post
import com.example.step5app.presentation.bottomBar.BottomBar
import com.example.step5app.presentation.feed.FeedUiState
import com.example.step5app.presentation.feed.FeedViewModel
import com.example.step5app.presentation.navigation.Screen
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
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }

    // Load more when reaching bottom
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
        topBar = {
            TopBar(
                onSettingsClick = onSettingsClick,
                withSearch = true,
                onConfirmClick = { viewModel.searchPosts(it) }
            )
        },
        bottomBar = { BottomBar(navController = navController) },
        floatingActionButton = {
            if (showScrollToTop) {
                ScrollToTopFab {
                    coroutineScope.launch { listState.animateScrollToItem(0) }
                }
            }
        }
    ) { paddingValues ->
        FeedContent(
            uiState = uiState,
            listState = listState,
            modifier = Modifier.padding(paddingValues),
            onRetry = { viewModel.loadPosts() },
            onFilterClick = { viewModel.toggleFilterDropdown() },
            onFilterSelect = { viewModel.selectFilter(it) },
            onCategorySelect = { viewModel.selectCategory(it) },
            getImageUrl = { viewModel.getImageUrl(it) },
            onPostClick = { feedId ->
                navController.navigate(Screen.FeedDetails.createRoute(feedId))
            },
            onLockClick = {
                navController.navigate(Screen.Profile.createRoute(2))
            }
        )
    }
}


@Composable
fun FeedContent(
    uiState: FeedUiState,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onFilterClick: () -> Unit,
    onFilterSelect: (String) -> Unit,
    onCategorySelect: (Category) -> Unit,
    getImageUrl: (Int) -> String,
    onPostClick: (Int) -> Unit,
    onLockClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when {
            uiState.isLoading -> FullScreenLoader()
            uiState.errorMessage != null -> FullScreenError(uiState.errorMessage, onRetry)
            else -> {
                FilterSection(
                    uiState = uiState,
                    onFilterClick = onFilterClick,
                    onFilterSelect = onFilterSelect
                )
                CategoryRow(
                    categories = uiState.categories,
                    selectedCategory = uiState.selectedCategory,
                    onCategorySelect = onCategorySelect
                )
                PostList(
                    posts = uiState.posts,
                    hasMorePosts = uiState.hasMorePosts,
                    listState = listState,
                    getImageUrl = getImageUrl,
                    onPostClick = onPostClick,
                    onLockClick = onLockClick
                )
            }
        }
    }
}

@Composable
fun FilterSection(
    uiState: FeedUiState,
    onFilterClick: () -> Unit,
    onFilterSelect: (String) -> Unit
) {
    Box {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 6.dp)
//                .clickable { onFilterClick() }
                ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.filter),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
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
            onDismissRequest = { onFilterClick() }
        ) {
            uiState.filterOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = { onFilterSelect(option) }
                )
            }
        }
    }
}

@Composable
fun CategoryRow(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelect: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp)
    ) {
        categories.forEach { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelect(category) },
                label = {
                    Text(
                        text = category.name,
                        fontSize = 14.sp,
                        color = if (category == selectedCategory) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.tertiary
                    )
                },
                modifier = Modifier
                    .height(35.dp)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(0.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                    containerColor = Color.Transparent
                ),
                border = if (category == selectedCategory)
                    BorderStroke(1.dp, Color.Transparent)
                else BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary)
            )
        }
    }
}

@Composable
fun PostList(
    posts: List<Post>,
    hasMorePosts: Boolean,
    listState: LazyListState,
    getImageUrl: (Int) -> String,
    onPostClick: (Int) -> Unit,
    onLockClick: () -> Unit
) {
    LazyColumn(state = listState) {
        items(posts) { post ->
            PostCard(
                post = post,
                imageUrl = getImageUrl(post.id - 1),
                onPostClick = onPostClick,
                onLockClick = onLockClick
            )
        }
        if (hasMorePosts) {
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

@Composable
fun PostCard(
    post: Post,
    imageUrl: String,
    onPostClick: (Int) -> Unit,
    onLockClick: () -> Unit
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = !post.isLocked) {
                if (!post.isLocked){
                    onPostClick(post.id)
                }else{
                    onLockClick()
                }
        },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Box {
            Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = post.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.logo),
                    error = painterResource(R.drawable.logo)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = post.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = post.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                PostDate(post.updatedAt)
            }

            // Lock overlay
            if (post.isLocked) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                Color.Black.copy(alpha = 0.7f),
                                CircleShape
                            )
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.lock),
                            contentDescription = "Locked Content",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun PostDate(date: String) {
    val parsedDate = OffsetDateTime.parse(date)
    val formatter = DateTimeFormatter.ofPattern("HH:mm. dd.MM.yy")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp, bottom = 4.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            text = parsedDate.format(formatter),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun FullScreenLoader() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun FullScreenError(message: String?, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message ?: "Error loading posts",
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}


@Composable
fun ScrollToTopFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
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
