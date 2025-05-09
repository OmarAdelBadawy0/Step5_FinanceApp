import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.step5app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen() {
    val title1 = stringResource(R.string.title_1)
    val desc1 = stringResource(R.string.description_1)
    val date1 = stringResource(R.string.may_6_2023)

    val title2 = stringResource(R.string.title_2)
    val desc2 = stringResource(R.string.description_2)
    val date2 = stringResource(R.string.may_5_2023)

    val title3 = stringResource(R.string.title_3)
    val desc3 = stringResource(R.string.description_3)
    val date3 = stringResource(R.string.may_4_2023)

    val filterstr = stringResource(R.string.filter)

    // Then use them in remember
    val posts = remember {
        listOf(
            Post(title1, desc1, date1),
            Post(title2, desc2, date2),
            Post(title3, desc3, date3)
        )
    }
    val selectedTab = remember { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(R.string.feed),
        stringResource(R.string.courses),
        stringResource(R.string.network),
        stringResource(R.string.profile)
    )
    val searchText = remember { mutableStateOf("") }
    val selectCategory = remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(stringResource(R.string.today),
        stringResource(R.string.this_week),
        stringResource(R.string.last_week),
        stringResource(R.string.this_month)
    )
    var selectedOption by remember { mutableStateOf(filterstr) }

    val isDarkTheme = isSystemInDarkTheme()


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .padding(horizontal = 10.dp, vertical = 20.dp),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(50.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.logo),
                                contentDescription = stringResource(R.string.logo_description),
                                contentScale = ContentScale.Crop
                            )
                        }
                    },
                    title = {
                        OutlinedTextField(
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .padding(start = 8.dp, top = 8.dp)
                                .fillMaxHeight(),
                            value = searchText.value,
                            onValueChange = { searchText.value = it },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.search),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                )
                            },
                            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                            trailingIcon = {
                                if (searchText.value.isNotEmpty()) {
                                    IconButton(onClick = { searchText.value = "" }) {
                                        Icon(
                                            painter = painterResource(R.drawable.x),
                                            contentDescription = stringResource(R.string.clear_search),
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                            singleLine = true,
                        )
                    },
                    actions = {
                        IconButton(onClick = { /* Open settings */ }) {
                            Icon(
                                painter = painterResource(R.drawable.gear),
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(28.dp)

                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab.intValue == index,
                        onClick = { selectedTab.intValue = index },
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
                                if (selectedTab.intValue == index) {
                                    // Draw black stroke on top
                                    drawLine(
                                        color = if (isDarkTheme) Color.White else Color.Black,
                                        start = Offset(0f, 0f),
                                        end = Offset(size.width, 0f),
                                        strokeWidth = 5.dp.toPx(),
                                    )
                                }
                            }
                            .background(
                                if (selectedTab.intValue == index) Color.Gray.copy(alpha = 0.2f)
                                else Color.Transparent,
                                shape = RectangleShape
                            )
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
            // Filter Row
            Box {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 6.dp)
                        .clickable { expanded = true }, // open dropdown on click
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
                        text = selectedOption,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        painter = if (expanded) painterResource(R.drawable.arrow_right_short) else painterResource(R.drawable.arrow_down_short),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(26.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedOption = option
                                expanded = false
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
                listOf(stringResource(R.string.category1),
                    stringResource(R.string.category2),
                    stringResource(R.string.category3),
                    stringResource(R.string.category4)
                ).forEach { category ->
                    FilterChip(
                        selected = category == selectCategory.value, // First chip selected as in the image
                        onClick = {
                            if (category == selectCategory.value) selectCategory.value = "" else selectCategory.value = category
                                  },
                        label = { Text(text = category,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color =
                                if (category == selectCategory.value) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(0.dp)) },
                        modifier = Modifier
                            .height(40.dp)
                            .minimumInteractiveComponentSize()
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.tertiary, // Gold color for selected chip
                            containerColor = Color.Transparent
                        ),
                        border =
                            if (category == selectCategory.value) BorderStroke(1.dp, Color.Transparent)
                            else BorderStroke((1.5).dp, MaterialTheme.colorScheme.tertiary
                        ),
                    )
                }
            }

            // List of Items
            LazyColumn {
                items(posts.reversed()) { item ->
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
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
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

// Data classes
data class Post(
    val title: String,
    val description: String,
    val date: String
)

@Composable
fun generateSamplePosts(): List<Post> {
    return listOf(
        Post(stringResource(R.string.category1), "DESCRIPTION 1", "May 6, 2023"),
        Post("# TITLE 2", "DESCRIPTION 2", "May 5, 2023"),
        Post("# TITLE 3", "DESCRIPTION 3 main sorry tree game sorry test the description length how much is it to be maximum ...", "May 4, 2023")
    )
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    FeedScreen()
}