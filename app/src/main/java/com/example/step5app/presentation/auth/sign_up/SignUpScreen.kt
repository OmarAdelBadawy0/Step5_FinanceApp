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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.step5app.R
import com.example.step5app.presentation.common.EmailField
import com.example.step5app.presentation.common.NameFieldsRow
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

        OutlinedTextField(
            value = uiState.password,
            onValueChange = {viewModel.updatePassword(it)},
            label = { Text(stringResource(R.string.password), color = MaterialTheme.colorScheme.onSurface) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.lock),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                ) },
            trailingIcon = {
                IconButton( onClick = {viewModel.updateIsPasswordVisible(!uiState.isPasswordVisible)}) {
                    Icon(
                        painter = painterResource(R.drawable.eye),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = {viewModel.updateConfirmPassword(it)},
            label = { Text(stringResource(R.string.confirm_password), color = MaterialTheme.colorScheme.onSurface) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.lock),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                ) },
            trailingIcon = {
                IconButton( onClick = {viewModel.updateIsConfirmPasswordVisible(!uiState.isConfirmPasswordVisible)}) {
                    Icon(
                        painter = painterResource(R.drawable.eye),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (uiState.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            )
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