package com.example.step5app.presentation.topBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.step5app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    viewModel: TopBarViewModel = hiltViewModel(),
    onSettingsClick: () -> Unit = {}
) {
    val searchText by viewModel.searchText

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
                    value = searchText,
                    onValueChange = viewModel::updateSearchText,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.search),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp,
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = viewModel::clearSearch) {
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
                IconButton(onClick = onSettingsClick ) {
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
}