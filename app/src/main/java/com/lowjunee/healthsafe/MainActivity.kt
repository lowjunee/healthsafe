package com.lowjunee.healthsafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.lowjunee.healthsafe.ui.theme.HealthSafeTheme
import com.lowjunee.healthsafe.ui.LoginScreen
import com.lowjunee.healthsafe.ui.SignUpScreen
import com.lowjunee.healthsafe.ui.HomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthSafeTheme {
                HealthSafeApp()
            }
        }
    }
}

@Composable
fun HealthSafeApp() {
    var showLoginScreen by remember { mutableStateOf(true) }
    var showSignUpScreen by remember { mutableStateOf(false) }
    var showHomeScreen by remember { mutableStateOf(false) }

    Surface(color = MaterialTheme.colorScheme.background) {
        when {
            showLoginScreen -> {
                LoginScreen(
                    onSignInClick = {
                        // Navigate to Home screen after successful sign-in
                        showLoginScreen = false
                        showHomeScreen = true
                    },
                    onRegisterClick = {
                        // Navigate to Sign Up screen
                        showLoginScreen = false
                        showSignUpScreen = true
                    }
                )
            }
            showSignUpScreen -> {
                SignUpScreen(onSignUpSuccess = {
                    // Navigate back to Login screen after successful sign-up
                    showSignUpScreen = false
                    showLoginScreen = true
                })
            }
            showHomeScreen -> {
                HomeScreen(
                    onNavigate = { screen ->
                        // Handle navigation based on the screen parameter
                        when (screen) {
                            "metrics" -> { /* Navigate to My Metrics screen */ }
                            "medications" -> { /* Navigate to Medications screen */ }
                            "past_visits" -> { /* Navigate to Past Visits screen */ }
                            "sos" -> { /* Navigate to SOS screen */ }
                            "qr_release" -> { /* Navigate to QR Release screen */ }
                        }
                    },
                    onNavigateBack = {
                        // Navigate back to the Login screen if the user logs out
                        showHomeScreen = false
                        showLoginScreen = true
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HealthSafeAppPreview() {
    HealthSafeTheme {
        HealthSafeApp()
    }
}
