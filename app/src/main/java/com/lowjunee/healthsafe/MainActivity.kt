package com.lowjunee.healthsafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.lowjunee.healthsafe.ui.theme.HealthSafeTheme
import com.lowjunee.healthsafe.ui.screen.LoginScreen
import com.lowjunee.healthsafe.ui.screen.SignUpScreen
import com.lowjunee.healthsafe.ui.screen.HomeScreen
import com.lowjunee.healthsafe.ui.theme.BottomNavigationBar
import androidx.compose.ui.Modifier

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
    var selectedTab by remember { mutableStateOf("home") }

    Surface(color = MaterialTheme.colorScheme.background) {
        when {
            showLoginScreen -> {
                LoginScreen(
                    onSignInClick = {
                        // Navigate to Home screen after successful sign-in
                        showLoginScreen = false
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
            else -> {
                // Main App Content with Bottom Navigation Bar
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            selectedTab = selectedTab,
                            onTabSelected = { tab ->
                                selectedTab = tab
                            }
                        )
                    }
                ) { paddingValues ->
                    // Main content based on selected tab
                    when (selectedTab) {
                        "home" -> HomeScreen(
                            onNavigate = { screen ->
                                when (screen) {
                                    "metrics" -> { /* Navigate to My Metrics screen */ }
                                    "medications" -> { /* Navigate to Medications screen */ }
                                    "past_visits" -> { /* Navigate to Past Visits screen */ }
                                    "sos" -> { /* Navigate to SOS screen */ }
                                    "qr_release" -> { /* Navigate to QR Release screen */ }
                                }
                            },
                            modifier = Modifier.padding(paddingValues)
                        )
                        //"tips" -> TipsScreen(modifier = Modifier.padding(paddingValues))
                        //"profile" -> ProfileScreen(modifier = Modifier.padding(paddingValues))
                        //"settings" -> SettingsScreen(modifier = Modifier.padding(paddingValues))
                    }
                }
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
