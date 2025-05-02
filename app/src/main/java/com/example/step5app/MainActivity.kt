package com.example.step5app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.step5app.screens.auth.AuthScreen
import com.example.step5app.ui.theme.Step5AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Step5AppTheme(darkTheme = true) {
                    AuthScreen()
            }
        }
    }
}



