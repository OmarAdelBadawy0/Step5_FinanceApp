package com.example.step5app.presentation.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.step5app.presentation.common.SectionTitle
import com.example.step5app.presentation.common.ToggleRow

@Composable
fun NotificationScreen() {


    Column {
        SectionTitle("PREFERENCES")


        ToggleRow(
            text = "Gold Feeds",
            icon = null,
            selected = false,
            onClick = { }
        )

        ToggleRow(
            text = "Egyptian Stocks Feeds",
            icon = null,
            selected = false,
            onClick = { }
        )

        ToggleRow(
            text = "Bitcoin Feeds",
            icon = null,
            selected = true,
            onClick = { }
        )
    }

}