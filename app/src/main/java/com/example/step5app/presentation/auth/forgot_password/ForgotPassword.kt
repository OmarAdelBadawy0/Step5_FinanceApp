package com.example.step5app.presentation.auth.forgot_password

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.step5app.R
import com.example.step5app.presentation.common.EmailField
import com.example.step5app.presentation.common.OtpDialog
import com.example.step5app.presentation.common.PasswordField
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onForgotPasswordSuccess: () -> Unit = {},
    @ApplicationContext context: Context = LocalContext.current
) {


    LaunchedEffect(viewModel.errorMessage) {
        if (viewModel.errorMessage.isNotEmpty()) {
            Toast.makeText(context, viewModel.errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    LaunchedEffect(viewModel.passwordChangedSuccess) {
        if (viewModel.passwordChangedSuccess) {
            onForgotPasswordSuccess()
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (viewModel.forgotScreensState.screen == 1) {
                GetEmail(viewModel)
            }else if (viewModel.forgotScreensState.screen == 2) {
                GetOtp(viewModel)
            }else if (viewModel.forgotScreensState.screen == 3) {
                SetNewPassword(viewModel)
            }else{
                Text(text = "Something went wrong")
            }
        }
    }
}

@Composable
fun GetEmail(viewModel: ForgotPasswordViewModel = hiltViewModel()){
    val invalidEmailMessage = stringResource(R.string.please_enter_a_valid_email)
    Text(
        text = stringResource(R.string.write_your_email_to_reset_the_password),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 34.dp),
        style = MaterialTheme.typography.titleLarge
    )

    EmailField(
        email = viewModel.email,
        onEmailChange = { viewModel.updateEmail(it) },
        modifier = Modifier.padding(horizontal = 12.dp)
    )

    Button(
        onClick = {
            viewModel.validateEmail()
            if (viewModel.validateEmail()){
//                viewModel.updateForgotScreensState(ForgotPasswordViewModel.ForgotScreens(screen = 2))
                viewModel.requestForgetPassword()
            }else{
                viewModel.updateErrorMessage(invalidEmailMessage)
            }
        },
        modifier = Modifier.padding(top = 24.dp),
        shape = RoundedCornerShape(0.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ){
        Text(
            text = stringResource(R.string.reset_password),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun GetOtp(viewModel: ForgotPasswordViewModel = hiltViewModel()){
    Text(
        text = stringResource(R.string.enter_the_otp_sent_to_your_email),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 34.dp),
        style = MaterialTheme.typography.titleLarge
    )


    OtpDialog(
        otpCode = viewModel.otpCode,
        onOtpCodeChange = { viewModel.updateOtpCode(it) },
        onConfirm = {
            viewModel.verifyForgetPassword()
        },
        onDismiss = {
            viewModel.updateForgotScreensState(ForgotPasswordViewModel.ForgotScreens(screen = 1))
        }
    )
}


@Composable
fun SetNewPassword(
    viewModel: ForgotPasswordViewModel = hiltViewModel()
    ){
    Text(
        text = stringResource(R.string.write_your_new_password),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 34.dp),
        style = MaterialTheme.typography.titleLarge
    )

    PasswordField(
        value = viewModel.password,
        onValueChange = { viewModel.updatePassword(it) },
        onVisibilityToggle = { viewModel.updateIsPasswordVisible(!viewModel.isPasswordVisible) },
        isVisible = viewModel.isPasswordVisible,
        label = stringResource(R.string.new_password),
        modifier = Modifier.padding(horizontal = 12.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    PasswordField(
        value = viewModel.confirmPassword,
        onValueChange = { viewModel.updateConfirmPassword(it) },
        onVisibilityToggle = { viewModel.updateIsConfirmPasswordVisible(!viewModel.isConfirmPasswordVisible) },
        isVisible = viewModel.isConfirmPasswordVisible,
        label = stringResource(R.string.confirm_password),
        modifier = Modifier.padding(horizontal = 12.dp)
    )

    Spacer(modifier = Modifier.height(24.dp))


    Button(
        onClick = {
            viewModel.changeForgetPassword()
        },
        modifier = Modifier.padding(top = 24.dp),
        shape = RoundedCornerShape(0.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        ),
        enabled = viewModel.password == viewModel.confirmPassword
    ){
        Text(
            text = stringResource(R.string.reset_password),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
        )
    }


}


@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen()
}