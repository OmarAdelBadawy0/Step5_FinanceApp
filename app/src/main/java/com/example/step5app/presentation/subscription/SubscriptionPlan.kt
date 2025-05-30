package com.example.step5app.presentation.subscription

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.step5app.R
import com.example.step5app.domain.model.Subscription
import com.example.step5app.presentation.common.SectionTitle
import androidx.compose.foundation.lazy.items


@Composable
fun SubscriptionPlan() {

    val subscriptions: List<Subscription> = listOf(
        Subscription("Basic", 50.0, "PER MONTH", listOf("Feature A", "Feature B", "Feature C"), false),
        Subscription("Premium", 100.0, "PER MONTH", listOf("Feature X", "Feature Y", "Feature Z"), true),
        Subscription("Pro", 150.0, "PER MONTH", listOf("All Features", "Priority Support", "More"), false)
    )

    SectionTitle(stringResource(R.string.my_plan))
    Text(
        stringResource(R.string.you_don_t_have_any_subscription_yet),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    Spacer(modifier = Modifier.height(25.dp))

    SectionTitle(stringResource(R.string.subscription_plans))
    Spacer(modifier = Modifier.height(25.dp))


    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(subscriptions) { subscription ->
            SubscriptionCard(subscription)
        }
    }
}



@Composable
fun SubscriptionCard(subscription: Subscription) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Header Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(subscription.name, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 22.sp)
                    Text(subscription.duration, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(Modifier.height(8.dp))
                    Text(stringResource(R.string.le, subscription.price), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Features
            subscription.features.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.check_circle),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.onSurface)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Subscribe Button
            Button(
                onClick = { /* handle subscribe */ },
                colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.subscribe),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}