package com.example.step5app.presentation.subscription

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.step5app.R
import com.example.step5app.presentation.common.SectionTitle
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.step5app.domain.model.Plan


@Composable
fun SubscriptionPlan(
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val plans = viewModel.plans.collectAsState()
    val error = viewModel.error.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(error) {
        error.value?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

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
        items(plans.value) { plan ->
            SubscriptionCard(plan, stringResource(R.string.per_month), plan.price)
            Spacer(modifier = Modifier.width(16.dp))
            SubscriptionCard(plan, stringResource(R.string.per_year), plan.price * 12 * (1 - plan.annualDiscount/100))
        }
    }
}



@Composable
fun SubscriptionCard(plan: Plan, duration : String, price: Double) {
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
                    Text(
                        plan.name,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 22.sp)
                    Text(
                        text = duration,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.le, price),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Spacer(Modifier.height(16.dp))

            val features = plan.description?.split(",") ?: emptyList()  // Split the description into features
            // Features
            features.forEach {
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
                    Text(text = it.trim().uppercase(), color = MaterialTheme.colorScheme.onSurface)
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
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}