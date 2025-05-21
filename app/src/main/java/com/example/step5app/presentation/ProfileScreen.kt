package com.example.step5app.presentation

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.step5app.R
import com.example.step5app.presentation.bottomBar.BottomBar
import com.example.step5app.presentation.topBar.TopBar

@Composable
fun ProfileScreen(
    onNavigateToSubscription: () -> Unit,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = { BottomBar() }
    ) { paddingValues ->

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 34.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .clickable(onClick = onBackClick)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = stringResource(R.string.logo_description),
                    contentScale = ContentScale.Crop
                )
            }
            IconButton(onClick = { onSettingsClick }) {
                Icon(
                    painterResource(R.drawable.gear),
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.scale(1.6f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(54.dp))


            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Person",
                        fontSize = 34.sp
                    )
                }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .wrapContentWidth(),
                    shape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(horizontal = 28.dp) // Remove default button padding
                ) {
                    Box(
                        modifier = Modifier.wrapContentWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Logout",
                            modifier = Modifier.widthIn(min = 40.dp),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))


            Column(modifier = Modifier.fillMaxWidth()) {
                // First TabRow (Notifications/Delete Account)
                TabRow(
                    selectedTabIndex = 0,
                    containerColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
                    divider = {}, // Remove default divider
                    indicator = {} // Remove default indicator
                ) {
                    Tab(
                        selected = selectedTabIndex == 3,
                        onClick = {
                            selectedTabIndex = 3
                        },
                        text = {
                            Text(
                                "NOTIFICATIONS",
                                color =
                                    if (selectedTabIndex == 3) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(start = 30.dp, end = 10.dp)
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(37.dp)
                            .background(
                                if (selectedTabIndex == 3) MaterialTheme.colorScheme.tertiary
                                else Color.Transparent
                            )
                            )
                    Tab(
                        selected = selectedTabIndex == 4,
                        onClick = {
                            selectedTabIndex = 4
                        },
                        text = {
                            Text(
                                "DELETE ACCOUNT",
                                color =
                                    if (selectedTabIndex == 4) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(start = 10.dp, end = 30.dp)
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(37.dp)
                            .background(
                                if (selectedTabIndex == 4) MaterialTheme.colorScheme.tertiary
                                else Color.Transparent
                            )
                    )
                }

                // Second TabRow (Profile/My Courses/Subscription)
                TabRow(
                    selectedTabIndex = selectedTabIndex, // You need to manage this state
                    modifier = Modifier.fillMaxWidth().height(34.dp),
                    containerColor = Color.Transparent,
                    divider = {}, // Remove default divider
                    indicator = {} // Remove default indicator
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = {
                            selectedTabIndex = 0
                        },
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 4.dp)
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(38.dp)
                            .background(if (selectedTabIndex == 0) MaterialTheme.colorScheme.tertiary else Color.Transparent),
                        text = {
                            Text("PROFILE",
                                color = if (selectedTabIndex == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = {
                            selectedTabIndex = 1
                        },
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 4.dp)
                            .fillMaxHeight()
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(38.dp)
                            .background(if (selectedTabIndex == 1) MaterialTheme.colorScheme.tertiary else Color.Transparent),
                        text = {
                            Text("MY COURSES",
                                color = if (selectedTabIndex == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTabIndex == 2,
                        onClick = {
                            selectedTabIndex = 2
                            onNavigateToSubscription()
                        },
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 4.dp)
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(38.dp)
                            .background(if (selectedTabIndex == 2) MaterialTheme.colorScheme.tertiary else Color.Transparent),
                        text = {
                            Text(
                                "SUBSCRIPTION",
                                color = if (selectedTabIndex == 2) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.tertiary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Personal Details Section
            Text("PERSONAL DETAILS", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { /* Save changes */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("SAVE CHANGES")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Change Password Section
            Text("CHANGE PASSWORD", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            var oldPassword by remember { mutableStateOf("") }
            var newPassword by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }

            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                label = { Text("Old Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm New Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { /* Change password */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("CHANGE PASSWORD")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Danger Zone
            Text(
                "DANGER ZONE",
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.error)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Delete account */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("DELETE ACCOUNT")
            }
        }
    }
}