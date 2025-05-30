package com.example.step5app.presentation.deleteAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.step5app.R

@Composable
fun DeleteAccountSection(
    onDeleteClick: () -> Unit,
    onSendCodeClick: () -> Unit
) {

    var code by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Warning text
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
                    append(stringResource(R.string.warning))
                }
                append(stringResource(R.string.if_you_delete_your_account_you_will_be_unsubscribed_from_all_of_your_courses_and_will_lose_access_to_your_account_and_data_associated_with_your_account))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.forever))
                }
            },
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Instruction text
        Text(
            text = stringResource(R.string.if_you_sure_to_delete_your_account_please_enter_the_6_digit_code_we_sent_to_test1_email_com_to_delete_your_account),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 6-digit code TextField
        TextField(
            value = code,
            onValueChange = {
                if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                    code = it
                }
            },
            label = { Text(stringResource(R.string._6_digit_code), color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp) },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth(),

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Button(
                onClick = onDeleteClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(stringResource(R.string.delete_account), fontWeight = FontWeight.Bold, color = Color.Red)
            }

            Button(
                onClick = onSendCodeClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.weight(0.75f),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(stringResource(R.string.send_code), fontWeight = FontWeight.Bold)
            }
        }
    }
}
