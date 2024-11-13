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
import com.google.firebase.FirebaseApp
import com.lowjunee.healthsafe.model.samplePastVisitsList
import com.lowjunee.healthsafe.ui.screen.PastVisitsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

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
    var selectedScreen by remember { mutableStateOf("home") }

    Surface(color = MaterialTheme.colorScheme.background) {
        when {
            showLoginScreen -> {
                LoginScreen(
                    onSignInClick = {
                        showLoginScreen = false
                    },
                    onRegisterClick = {
                        showLoginScreen = false
                        showSignUpScreen = true
                    }
                )
            }
            showSignUpScreen -> {
                SignUpScreen(onSignUpSuccess = {
                    showSignUpScreen = false
                    showLoginScreen = true
                })
            }
            else -> {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            selectedTab = selectedTab,
                            onTabSelected = { tab ->
                                selectedTab = tab
                                selectedScreen = tab
                            }
                        )
                    }
                ) { paddingValues ->
                    when (selectedScreen) {
                        "home" -> HomeScreen(
                            onNavigate = { screen ->
                                selectedScreen = screen
                            },
                            modifier = Modifier.padding(paddingValues)
                        )
                        "past_visits" -> PastVisitsScreen(
                            pastVisits = samplePastVisitsList,
                            onBackClick = {
                                selectedScreen = "home"
                            },
                            onAddPastVisit = {
                                println("Add Past Visit clicked")
                            }
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
