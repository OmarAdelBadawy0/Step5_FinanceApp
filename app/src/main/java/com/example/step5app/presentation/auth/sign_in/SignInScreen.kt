package com.example.step5app.presentation.auth.sign_in

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.step5app.R
import com.example.step5app.presentation.common.EmailField
import com.example.step5app.presentation.common.PasswordField
import com.example.step5app.ui.theme.Step5AppTheme

@Composable
fun SignInFields(
    viewModel: SignInViewModel = hiltViewModel(),
    onSignUp: () -> Unit,
    onSuccess: () -> Unit
) {

    val uiState by viewModel.loginUiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.isSuccessLogin) {
        if (uiState.isSuccessLogin) onSuccess()
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp, 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        EmailField(
            email = uiState.email,
            onEmailChange = { viewModel.onEmailChange(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            onVisibilityToggle = viewModel::onPasswordVisibilityChange,
            isVisible = uiState.isPasswordVisible,
            label = stringResource(R.string.password),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Checkbox(
                    checked = uiState.isRememberMeChecked,
                    onCheckedChange = {viewModel.onRememberMeCheckedChange()},
                    modifier = Modifier.size(24.dp),
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = MaterialTheme.colorScheme.onSurface,
                        checkedColor = MaterialTheme.colorScheme.onSurface,
                        checkmarkColor = MaterialTheme.colorScheme.onSurfaceVariant // or Color.White if you want it hidden
                    )
                )
                Text(text = stringResource(R.string.remember_me), fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
            }
            TextButton(onClick = { /* TODO: Forgot password */ }, ) {
                Text(text = stringResource(R.string.forgot_password), fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.loginUser() },
            modifier = Modifier.fillMaxWidth()
                .padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(text = stringResource(R.string.signin), color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignInFieldsPreview() {
    Step5AppTheme() {
        SignInFields(onSignUp = {}, onSuccess = {})
    }
}