package com.lowjunee.healthsafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lowjunee.healthsafe.ui.theme.HealthSafeTheme
import com.lowjunee.healthsafe.ui.screen.*
import com.lowjunee.healthsafe.ui.theme.BottomNavigationBar
import com.google.firebase.FirebaseApp
import com.lowjunee.healthsafe.data.FirestoreHelper

class MainActivity : ComponentActivity() {
    private val firestoreHelper = FirestoreHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            HealthSafeTheme {
                HealthSafeApp(firestoreHelper)
            }
        }
    }
}

@Composable
fun HealthSafeApp(firestoreHelper: FirestoreHelper) {
    var showLoginScreen by remember { mutableStateOf(true) }
    var showSignUpScreen by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("home") }
    var selectedScreen by remember { mutableStateOf("home") }

    Surface(color = MaterialTheme.colorScheme.background) {
        when {
            showLoginScreen -> LoginScreen(
                onSignInClick = { showLoginScreen = false },
                onRegisterClick = { showLoginScreen = false; showSignUpScreen = true }
            )
            showSignUpScreen -> SignUpScreen(onSignUpSuccess = {
                showSignUpScreen = false
                showLoginScreen = true
            })
            else -> Scaffold(
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
                    "home" -> HomeScreen(onNavigate = { selectedScreen = it })
                    "past_visits" -> PastVisitsScreen(
                        onBackClick = { selectedScreen = "home" },
                        onAddPastVisit = { println("Add Past Visit clicked") }
                    )
                    "metrics" -> MyMetricsScreen(
                        onMetricClick = { metricType ->
                            when (metricType) {
                                "Heart Rate" -> selectedScreen = "heart_rate_metrics"
                                "Blood Pressure" -> selectedScreen = "blood_pressure_metrics"
                                "ECG" -> selectedScreen = "ecg_metrics"
                                "Steps" -> selectedScreen = "steps_metrics"
                                "Calories Burned" -> selectedScreen = "calories_burned_metrics"
                                "Flights Climbed" -> selectedScreen = "flights_climbed_metrics"
                            }
                        },
                        onBackClick = { selectedScreen = "home" },
                        modifier = Modifier.padding(paddingValues)
                    )
                    "heart_rate_metrics" -> HeartRateMetricsScreen(
                        firestoreHelper = firestoreHelper,
                        userId = "user123",
                        onBackClick = { selectedScreen = "metrics" }
                    )
                    "blood_pressure_metrics" -> BloodPressureMetricsScreen(
                        firestoreHelper = firestoreHelper,
                        userId = "user123",
                        onBackClick = { selectedScreen = "metrics" }
                    )
                    "ecg_metrics" -> ECGMetricsScreen(
                        firestoreHelper = firestoreHelper,
                        userId = "user123",
                        onBackClick = { selectedScreen = "metrics" }
                    )
                    "steps_metrics" -> StepsMetricsScreen(
                        firestoreHelper = firestoreHelper,
                        userId = "user123",
                        onBackClick = { selectedScreen = "metrics" }
                    )
                    "calories_burned_metrics" -> CaloriesBurnedMetricsScreen(
                        firestoreHelper = firestoreHelper,
                        userId = "user123",
                        onBackClick = { selectedScreen = "metrics" }
                    )
                    "flights_climbed_metrics" -> FlightsClimbedMetricsScreen(
                        firestoreHelper = firestoreHelper,
                        userId = "user123",
                        onBackClick = { selectedScreen = "metrics" }
                    )
                    "profile" -> ProfileScreen(
                        firestoreHelper = firestoreHelper,
                        userId = "user123",
                        onLinkDataSuccess = { selectedScreen = "metrics" }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HealthSafeAppPreview() {
    HealthSafeTheme {
        HealthSafeApp(FirestoreHelper())
    }
}
