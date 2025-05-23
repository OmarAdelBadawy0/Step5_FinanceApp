package com.example.step5app.presentation.profile

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.step5app.R
import com.example.step5app.presentation.bottomBar.BottomBar
import com.example.step5app.presentation.common.EmailField
import com.example.step5app.presentation.common.NameFieldsRow
import com.example.step5app.presentation.common.PasswordField
import com.example.step5app.presentation.common.SectionTitle

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
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
                    contentPadding = PaddingValues(horizontal = 28.dp), // Remove default button padding
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Box(
                        modifier = Modifier.wrapContentWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.logout),
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
                    selectedTabIndex = uiState.selectedTabIndex,
                    containerColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
                    divider = {}, // Remove default divider
                    indicator = {} // Remove default indicator
                ) {
                    Tab(
                        selected = uiState.selectedTabIndex == 3,
                        onClick = {
                            viewModel.updateSelectedTabIndex(3)
                        },
                        text = {
                            Text(
                                "NOTIFICATIONS",
                                color =
                                    if (uiState.selectedTabIndex == 3) MaterialTheme.colorScheme.onPrimary
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
                                if (uiState.selectedTabIndex == 3) MaterialTheme.colorScheme.tertiary
                                else Color.Transparent
                            )
                            )
                    Tab(
                        selected = uiState.selectedTabIndex == 4,
                        onClick = {
                            viewModel.updateSelectedTabIndex(4)
                        },
                        text = {
                            Text(
                                "DELETE ACCOUNT",
                                color =
                                    if (uiState.selectedTabIndex == 4) MaterialTheme.colorScheme.onPrimary
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
                                if (uiState.selectedTabIndex == 4) MaterialTheme.colorScheme.tertiary
                                else Color.Transparent
                            )
                    )
                }

                // Second TabRow (Profile/My Courses/Subscription)
                TabRow(
                    selectedTabIndex = uiState.selectedTabIndex, // You need to manage this state
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp),
                    containerColor = Color.Transparent,
                    divider = {}, // Remove default divider
                    indicator = {} // Remove default indicator
                ) {
                    Tab(
                        selected = uiState.selectedTabIndex == 0,
                        onClick = {
                            viewModel.updateSelectedTabIndex(0)
                        },
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 4.dp)
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(38.dp)
                            .background(if (uiState.selectedTabIndex == 0) MaterialTheme.colorScheme.tertiary else Color.Transparent),
                        text = {
                            Text("PROFILE",
                                color = if (uiState.selectedTabIndex == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = uiState.selectedTabIndex == 1,
                        onClick = {
                            viewModel.updateSelectedTabIndex(1)
                        },
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 4.dp)
                            .fillMaxHeight()
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(38.dp)
                            .background(if (uiState.selectedTabIndex == 1) MaterialTheme.colorScheme.tertiary else Color.Transparent),
                        text = {
                            Text("MY COURSES",
                                color = if (uiState.selectedTabIndex == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = uiState.selectedTabIndex == 2,
                        onClick = {
                            viewModel.updateSelectedTabIndex(2)
                        },
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 4.dp)
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(38.dp)
                            .background(if (uiState.selectedTabIndex == 2) MaterialTheme.colorScheme.tertiary else Color.Transparent),
                        text = {
                            Text(
                                "SUBSCRIPTION",
                                color = if (uiState.selectedTabIndex == 2) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.tertiary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Personal Details Section
            SectionTitle(stringResource(R.string.personal_details))

            Spacer(modifier = Modifier.height(8.dp))


            NameFieldsRow(
                firstName = uiState.firstName,
                onFirstNameChange = { viewModel.updateFirstName(it) },
                lastName = uiState.lastName,
                onLastNameChange = { viewModel.updateLastName(it) }
            )

            EmailField(
                email =  uiState.email,
                onEmailChange = { viewModel.updateEmail(it) }
            )


            OutlinedTextField(
                value = uiState.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = {
                    Text(
                        text = stringResource(R.string.phone_number_optional),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.phone),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                )
            )

            Button(
                onClick = { viewModel::saveProfileChanges },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(stringResource(R.string.save_changes), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(30.dp))


            SectionTitle(stringResource(R.string.change_password))


            PasswordField(
                value =  uiState.oldPassword,
                onValueChange = { viewModel.updateOldPassword(it) },
                onVisibilityToggle =  { viewModel::toggleOldPasswordVisibility },
                isVisible =  uiState.isOldPasswordVisible,
                label = stringResource(R.string.old_password)
            )

            PasswordField(
                value = uiState.newPassword,
                onValueChange = { viewModel.updateNewPassword(it) },
                onVisibilityToggle =  { viewModel::toggleNewPasswordVisibility },
                isVisible = uiState.isNewPasswordVisible,
                label = stringResource(R.string.new_password)
            )

            PasswordField(
                value = uiState.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                onVisibilityToggle =  { viewModel::toggleConfirmPasswordVisibility },
                isVisible = uiState.isConfirmPasswordVisible,
                label = stringResource(R.string.confirm_new_password)
            )

            Button(
                onClick = { viewModel::changePassword },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(stringResource(R.string.change_password), fontWeight = FontWeight.Bold)
            }


        }
    }
}