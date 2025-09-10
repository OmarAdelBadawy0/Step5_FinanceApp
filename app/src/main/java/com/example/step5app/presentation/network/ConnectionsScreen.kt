package com.example.step5app.presentation.network

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.step5app.R
import com.example.step5app.data.model.ChildConnection
import com.example.step5app.presentation.bottomBar.BottomBar
import com.example.step5app.presentation.common.SectionTitle
import com.example.step5app.presentation.topBar.TopBar

@Composable
fun ConnectionsScreen(
    connectionsViewModel: ConnectionsViewModel = hiltViewModel(),
    navController: NavController,
    onSettingsClick: () -> Unit,
) {
    val uiState by connectionsViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.message, uiState.errorMessage) {
        uiState.message?.let { message ->
            Toast.makeText(context, message.asString(context), Toast.LENGTH_SHORT).show()
            connectionsViewModel.onMessageShown()
        }
        uiState.errorMessage?.let { errorMessage ->
            Toast.makeText(context, errorMessage.asString(context), Toast.LENGTH_SHORT).show()
            connectionsViewModel.onErrorMessageShown()
        }
    }

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
                    text = uiState.firstName,
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
                        Text(
                            uiState.balance.toString(),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                SectionTitle(stringResource(R.string.my_connections))

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable(
                            enabled = uiState.inviteCode == null,
                            onClick = {
                                connectionsViewModel.fetchInviteCode()
                            }
                        ),
                ){
                    Text(
                        text = (if (uiState.inviteCode.isNullOrEmpty()) stringResource(R.string.generate_code)
                                else uiState.inviteCode).toString(),
                        Modifier.padding(8.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Spacer(modifier = Modifier.height(14.dp))


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.connections) { connection ->
                    ConnectionCard(
                        name = connection.firstName + " " + connection.lastName,
                        profit = connection.UserWallets.firstOrNull()?.balance ?: 0.0,
                        email = connection.email,
                        connections = connection.ChildrenConnections,
                        onDeleteClicked = { connectionsViewModel.deleteConnection(connection.id) }
                    )
                }

                // Add Connection Box inside the list
                item {
                    if (uiState.isAddingConnection) {
                        ConnectionCodeInputRow(
                            code = uiState.connectionAddingCode,
                            onCodeChange = { connectionsViewModel.onConnectionAddingCodeChange(it) },
                            onConfirm = {
                                connectionsViewModel.addConnection()
                                connectionsViewModel.onIsAddingConnectionChange(false)
                                connectionsViewModel.onConnectionAddingCodeChange("")
                            }
                        )
                    } else {
                        AddConnectionBox(
                            onClick = { connectionsViewModel.onIsAddingConnectionChange(true) }
                        )
                    }
                }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ConnectionCard(
    name: String,
    profit: Double,
    email: String,
    connections: List<ChildConnection>,
    onDeleteClicked: () -> Unit
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
                    modifier = Modifier.clickable { onDeleteClicked() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    LabeledItem(label = stringResource(R.string.profit), value = profit.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                    LabeledItem(label = stringResource(R.string.email), value = email.toString())
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            stringResource(R.string.connections),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(R.string.persons, connections.size),
                            color = Color.Gray,
                            fontSize = 10.sp,)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    connections.forEachIndexed { index, person ->
                        Text("${index + 1}. ${person.firstName} ${person.lastName}", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
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


@Composable
fun AddConnectionBox(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.add_connection),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(R.drawable.plus_circle),
                contentDescription = stringResource(R.string.add_icon),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .scale(1.3f)
                    .padding(4.dp)
            )
        }
    }
}


@Composable
fun ConnectionCodeInputRow(
    code: String,
    onCodeChange: (String) -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = code,
            onValueChange = onCodeChange,
            label = { Text(stringResource(R.string.enter_the_invite_code)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = MaterialTheme.colorScheme.tertiary
            ),
            singleLine = true,
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.tertiary)
                .weight(2f)
        )

        Button(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f)
                .height(56.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = onConfirm
        ) {
            Text(
                stringResource(R.string.confirm),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
