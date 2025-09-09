package com.example.step5app.presentation.feedDetails

import CategoryRow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.step5app.R
import com.example.step5app.presentation.bottomBar.BottomBar
import com.example.step5app.presentation.feed.FeedViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedDetailsScreen(
    feedDetailsViewModel: FeedDetailsViewModel = hiltViewModel(),
    feedViewModel: FeedViewModel = hiltViewModel(),
    feedId: Int,
    navController: NavController,
    onBackClick: () -> Unit,
) {
    val uiState by feedDetailsViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(feedId) {
        feedDetailsViewModel.loadFeedDetails(feedId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.feed_details),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left_short),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.scale(2.5f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 10.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = feedViewModel.getImageUrl(uiState.imageUrl?.id ?: 0),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(16.dp))

            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                CategoryRow(
                    categories = listOfNotNull(uiState.category),
                    selectedCategory = null,
                    onCategorySelect = {}
                )
            }

            Spacer(Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text( // Title
                    uiState.title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))

                PostDateDetails(uiState.date)

                Spacer(Modifier.height(20.dp))

                Text( // Description
                    text = uiState.description,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(Modifier.height(20.dp))

                Text( // Content
                    text = uiState.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun PostDateDetails(dateString: String?) {
    val formattedDate = remember(dateString) {
        if (!dateString.isNullOrBlank()) {
            try {
                val parsedDate = OffsetDateTime.parse(dateString)
                val formatter = DateTimeFormatter.ofPattern("HH:mm, dd.MM.yy")
                parsedDate.format(formatter)
            } catch (e: DateTimeParseException) { "" }
        } else { "" }
    }

    Text(
        formattedDate,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
    )
}