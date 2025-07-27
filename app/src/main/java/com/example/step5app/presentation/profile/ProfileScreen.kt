package com.example.step5app.presentation.profile

import android.widget.Toast
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
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.step5app.presentation.deleteAccount.DeleteAccountSection
import com.example.step5app.presentation.myCourses.MyCoursesScreen
import com.example.step5app.presentation.notifications.NotificationScreen
import com.example.step5app.presentation.subscription.SubscriptionPlan

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            Toast.makeText( viewModel.context, uiState.errorMessage, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
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
                IconButton(onClick =  onSettingsClick ) {
                    Icon(
                        painterResource(R.drawable.gear),
                        contentDescription = stringResource(R.string.settings),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.scale(1.6f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(45.dp))


            // Person name and logout button
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
                        text = stringResource(R.string.person),
                        fontSize = 34.sp
                    )
                }

                Button(
                    onClick = { viewModel.logout() },
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


            // sections grid and tabs
            Column(modifier = Modifier.minimumInteractiveComponentSize()) {
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
                                stringResource(R.string.notifications),
                                color =
                                    if (uiState.selectedTabIndex == 3) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
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
                                stringResource(R.string.delete_account),
                                color =
                                    if (uiState.selectedTabIndex == 4) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(start = 9.dp, end = 28.dp)
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
                            Text(stringResource(R.string.profile),
                                color = if (uiState.selectedTabIndex == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold,
                            ) }
                    )
                    Tab(
                        selected = uiState.selectedTabIndex == 1,
                        onClick = {
                            viewModel.updateSelectedTabIndex(1)
                        },
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 3.dp)
                            .fillMaxHeight()
                            .border(1.5.dp, MaterialTheme.colorScheme.tertiary)
                            .height(38.dp)
                            .background(if (uiState.selectedTabIndex == 1) MaterialTheme.colorScheme.tertiary else Color.Transparent),
                        text = {
                            Text(
                                stringResource(R.string.my_courses),
                                color = if (
                                    uiState.selectedTabIndex == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            ) }
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
                        content = {
                            Text(
                                stringResource(R.string.subscription),
                                color = if (uiState.selectedTabIndex == 2) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                            ) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.selectedTabIndex == 0) {
                PersonalDetails(
                    viewModel = viewModel,
                    uiState = uiState

                )
                Spacer(modifier = Modifier.height(30.dp))
                ChangePassword(
                    viewModel = viewModel,
                    uiState = uiState
                )
            }
            else if (uiState.selectedTabIndex == 1) {
                MyCoursesScreen()
            }
            else if (uiState.selectedTabIndex == 2) {
                SubscriptionPlan()
            }else if (uiState.selectedTabIndex == 3) {
                NotificationScreen()
            }
            else if (uiState.selectedTabIndex == 4) {
                DeleteAccountSection(
                    onDeleteClick = { /*TODO*/ },
                    onSendCodeClick = { /*TODO*/ },
                )
            }

        }
    }


}

@Composable
fun PersonalDetails(
    viewModel: ProfileViewModel = hiltViewModel(),
    uiState: ProfileUiState = viewModel.uiState.collectAsState().value
) {
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
        onEmailChange = { viewModel.updateEmail(it) },
        enabled = false
    )


    OutlinedTextField(
        value = uiState.phoneNumber.toString(),
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
        onClick = { viewModel.saveProfileChanges() },
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
}


@Composable
fun ChangePassword(
    viewModel: ProfileViewModel = hiltViewModel(),
    uiState: ProfileUiState = viewModel.uiState.collectAsState().value
) {
    SectionTitle(stringResource(R.string.change_password))


    PasswordField(
        value =  uiState.oldPassword,
        onValueChange = { viewModel.updateOldPassword(it) },
        onVisibilityToggle =  viewModel::toggleOldPasswordVisibility,
        isVisible =  uiState.isOldPasswordVisible,
        label = stringResource(R.string.old_password)
    )

    PasswordField(
        value = uiState.newPassword,
        onValueChange = { viewModel.updateNewPassword(it) },
        onVisibilityToggle =  viewModel::toggleNewPasswordVisibility,
        isVisible = uiState.isNewPasswordVisible,
        label = stringResource(R.string.new_password)
    )

    PasswordField(
        value = uiState.confirmPassword,
        onValueChange = { viewModel.updateConfirmPassword(it) },
        onVisibilityToggle =  viewModel::toggleConfirmPasswordVisibility,
        isVisible = uiState.isConfirmPasswordVisible,
        label = stringResource(R.string.confirm_new_password)
    )

    Button(
        onClick = { viewModel.changePassword() },
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




