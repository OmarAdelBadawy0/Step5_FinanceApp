import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.step5app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen() {
    val posts = remember { generateSamplePosts() }
    val selectedTab = remember { mutableIntStateOf(0) }
    val tabs = listOf("FEED", "COURSES", "NETWORK", "PROFILE")
    val searchText = remember { mutableStateOf("") }
    val selectCategory = remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Today", "This Week", "Last Week", "This Month")
    var selectedOption by remember { mutableStateOf("FILTER") }


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 20.dp)
                        .height(100.dp),
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
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(start = 8.dp),
                            value = searchText.value,
                            onValueChange = { searchText.value = it },
                            placeholder = {
                                Text(
                                    text = "SEARCH",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            },
                            trailingIcon = {
                                if (searchText.value.isNotEmpty()) {
                                    IconButton(onClick = { searchText.value = "" }) {
                                        Icon(
                                            painter = painterResource(R.drawable.x),
                                            contentDescription = "Clear search"
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
                containerColor = MaterialTheme.colorScheme.surface
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
                                    modifier = Modifier.size(24.dp)
                                )
                                1 -> Icon(
                                    painter = painterResource(R.drawable.book),
                                    contentDescription = title,
                                    modifier = Modifier.size(24.dp)
                                )
                                2 -> Icon(
                                    painter = painterResource(R.drawable.people),
                                    contentDescription = title,
                                    modifier = Modifier.size(24.dp)
                                )
                                3 -> Icon(
                                    painter = painterResource(R.drawable.person),
                                    contentDescription = title,
                                    modifier = Modifier.size(24.dp)
                                )
                                else -> Icon(Icons.Default.Search, title)
                            }
                        },
                        label = { Text(title) },
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
                                        color = Color.Black,
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
                            .size(22.dp)
                    )
                    Text(
                        text = selectedOption,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Icon(
                        painter = if (expanded) painterResource(R.drawable.arrow_right_short) else painterResource(R.drawable.arrow_down_short),
                        contentDescription = null,
                        tint = Color.Gray,
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
                listOf("CATEGORY1", "CATEGORY2", "CATEGORY3", "CATEGORY4").forEach { category ->
                    FilterChip(
                        selected = category == selectCategory.value, // First chip selected as in the image
                        onClick = { selectCategory.value = category    },
                        label = { Text(text = category,
                            fontSize = 12.sp,
                            color =  if (category == selectCategory.value) Color.White else Color(0xFF8B4513),
                            modifier = Modifier.padding(0.dp)) },
                        modifier = Modifier
                            .height(40.dp)
                            .width(115.dp)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF8B4513), // Brown color for selected chip
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color.Black,
                            disabledSelectedContainerColor = Color.Transparent
                        ),
                        border =
                            if (category == selectCategory.value) BorderStroke(1.dp, Color.Black)
                            else BorderStroke((1.5).dp, Color(0xFF8B4513)
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
                        Column {
                            // Placeholder for Image
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .background(Color.LightGray)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = item.title,
                                    fontSize = 16.sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                                Text(
                                    text = item.description,
                                    fontSize = 14.sp,
                                    color = Color.Gray
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
                                    color = Color.Gray
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

fun generateSamplePosts(): List<Post> {
    return listOf(
        Post("# TITLE 1", "DESCRIPTION 1", "May 6, 2023"),
        Post("# TITLE 2", "DESCRIPTION 2", "May 5, 2023"),
        Post("# TITLE 3", "DESCRIPTION 3 nmcsn jncsn nsnc gerg grgrg rggrgrg rgrgrg rgrgrgr grrgrg grrgrg ncjsn ncsj  cnsjcn njnj", "May 4, 2023")
    )
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    FeedScreen()
}