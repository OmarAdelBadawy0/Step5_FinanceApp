package com.example.step5app.presentation.auth.sign_up

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
import androidx.compose.runtime.Composable
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
import com.example.step5app.presentation.common.NameFieldsRow
import com.example.step5app.presentation.common.PasswordField
import com.example.step5app.ui.theme.Step5AppTheme

@Composable
fun SignUpFields(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.signUpUiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top =  130.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp, 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        NameFieldsRow(
            firstName = uiState.firstName,
            onFirstNameChange = { viewModel.updateFirstName(it) },
            lastName = uiState.lastName,
            onLastNameChange = { viewModel.updateLastName(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        EmailField(
            email = uiState.email,
            onEmailChange = { viewModel.updateEmail(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = uiState.password,
            onValueChange = { viewModel.updatePassword(it) },
            onVisibilityToggle = {viewModel.updateIsPasswordVisible(!uiState.isPasswordVisible)},
            isVisible = uiState.isPasswordVisible,
            label = stringResource(R.string.password),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.updateConfirmPassword(it) },
            onVisibilityToggle = {viewModel.updateIsConfirmPasswordVisible(!uiState.isConfirmPasswordVisible)},
            isVisible = uiState.isConfirmPasswordVisible,
            label = stringResource(R.string.confirm_password)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = uiState.isTermsChecked, onCheckedChange = {viewModel.updateIsTermsChecked(it)},
                modifier = Modifier
                    .size(20.dp)
                    .padding(8.dp),
                colors = CheckboxDefaults.colors(
                    uncheckedColor = MaterialTheme.colorScheme.onSurface,
                    checkedColor = MaterialTheme.colorScheme.onSurface,
                    checkmarkColor = MaterialTheme.colorScheme.onSurfaceVariant // or Color.White if you want it hidden
                )
            )
            Text(text = stringResource(R.string.terms_conditions), fontSize = 14.sp, modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.signUp()
                if (!uiState.isSuccessSignUp && uiState.errorMessage != null) {
                    Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show() }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(0.dp)

        ) {
            Text(text = stringResource(R.string.sign_up), color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SignUpFieldsPreview() {
    Step5AppTheme() {
        SignUpFields()
    }
}