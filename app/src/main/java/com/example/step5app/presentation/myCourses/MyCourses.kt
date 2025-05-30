package com.example.step5app.presentation.myCourses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.step5app.R

@Composable
fun MyCoursesScreen() {
    var visibleText by remember { mutableStateOf("") }
    val fullText = stringResource(R.string.coming_soon)

    // Start typing animation
    LaunchedEffect(Unit) {
        fullText.forEachIndexed { index, _ ->
            visibleText = fullText.substring(0, index + 1)
            kotlinx.coroutines.delay(100L) // Adjust speed here (ms per character)
        }
    }

    Spacer(modifier = Modifier.height(100.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .padding(15.dp)
                .background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = visibleText,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyCoursesScreenPreview() {
    MyCoursesScreen()
}