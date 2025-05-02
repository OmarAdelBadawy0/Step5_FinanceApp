package com.example.step5app.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AuthScreen() {
    var isSignUp by remember { mutableStateOf(false) }
    val cardOffset by animateDpAsState(
        targetValue = if (isSignUp) (-600).dp else 0.dp,
        animationSpec = tween(durationMillis = 700, easing = LinearOutSlowInEasing	)
    )
    val logoOffset by animateDpAsState(
        targetValue = if (isSignUp) (750).dp else (30).dp,
        animationSpec = tween(durationMillis = 0)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Image (Profile placeholder)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .offset(y = logoOffset)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
        }

        // Auth Form (Login or Signup)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.Center)
        ) {
            AnimatedContent(targetState = isSignUp, label = "AuthSwitch") { signup ->
                if (signup) {
                    SignUpFields()
                } else {
                    SignInFields()
                }
            }
        }

        // Bottom Card (Switch between login and register)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp, start = 20.dp, end = 20.dp)
                .align(Alignment.BottomCenter)
                .offset(y = cardOffset),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp, 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isSignUp) "GET STARTED WITH US!" else "WELCOME BACK!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isSignUp)
                        "SIGN UP IF YOU STILL DON’T HAVE AN ACCOUNT"
                    else
                        "SIGN IN TO GET ACCESS TO DATA AND INFORMATION",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { isSignUp = !isSignUp },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        text = if (isSignUp) "ALREADY HAVE ACCOUNT? LOGIN NOW" else "DON'T HAVE ACCOUNT? REGISTER NOW",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}





