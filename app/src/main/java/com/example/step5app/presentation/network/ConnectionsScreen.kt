package com.example.step5app.presentation.network

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.step5app.R
import com.example.step5app.domain.model.ConnectionData
import com.example.step5app.presentation.bottomBar.BottomBar
import com.example.step5app.presentation.common.SectionTitle
import com.example.step5app.presentation.topBar.TopBar

@Composable
fun ConnectionsScreen(
    navController: NavController,
    onSettingsClick: () -> Unit
) {
    val connectionsList = listOf(
        ConnectionData(
            name = "Ahmed Ali",
            profit = 150.0,
            numOfPlans = 3,
            connections = listOf("Youssef Kamal", "Nour Mohamed", "Omar Adel", "Layla Samir", "Kareem Nabil")
        ),
        ConnectionData(
            name = "Sara Mostafa",
            profit = 220.0,
            numOfPlans = 5,
            connections = listOf("Ali Hamdy", "Samir Hany", "Nada Khaled", "Hossam Gamal")
        ),
        ConnectionData(
            name = "Mahmoud Hassan",
            profit = 310.0,
            numOfPlans = 4,
            connections = listOf("Dina Adel", "Yara Ayman", "Tamer Farid")
        )
    )

    Scaffold(
        topBar = { TopBar(onSettingsClick = onSettingsClick) },
        bottomBar = {BottomBar(navController = navController)}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 14.dp),
        ) {
            // Top Row: Person Name and Profit
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.person_name),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(
                        R.string.profit),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 12.dp)
                            .minimumInteractiveComponentSize()
                    ) {
                        Text(stringResource(R.string.xx_le), color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle(stringResource(R.string.my_connections))

            Spacer(modifier = Modifier.height(14.dp))


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(connectionsList) { connection ->
                    ConnectionCard(
                        name = connection.name,
                        profit = connection.profit,
                        plans = connection.numOfPlans,
                        connections = connection.connections
                    )
                }

                // Add Connection Box inside the list
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { /* Add connection logic */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                stringResource(R.string.add_connection),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold)
                            Icon(
                                painterResource(R.drawable.plus_circle),
                                contentDescription = stringResource(R.string.add_icon),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .scale(1.3f)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConnectionCard(
    name: String,
    profit: Double,
    plans: Int,
    connections: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.person_circle),
                        contentDescription = stringResource(R.string.person_icon),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.scale(1.3f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(name, color = MaterialTheme.colorScheme.onSurface)
                }
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.rubbish_delete_icon),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                    // Delete action
                })
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    LabeledItem(label = stringResource(R.string.profit), value = profit.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    LabeledItem(label = stringResource(R.string.no_of_plans_sold), value = plans.toString())
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            stringResource(R.string.connections),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.persons, connections.size), color = Color.Gray, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    connections.forEachIndexed { index, person ->
                        Text("${index + 1}. $person", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// the small labels in the connection card
@Composable
fun LabeledItem(label: String, value: String) {
    Column {
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
        HorizontalDivider(
            modifier = Modifier
                .width(((label.length.toFloat() * 8f - 3 * kotlin.math.sqrt(label.length.toFloat())).dp))
                .height(2.dp),
            color = MaterialTheme.colorScheme.tertiary,
            thickness = 2.dp
        )
        Text(value, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
    }
}
