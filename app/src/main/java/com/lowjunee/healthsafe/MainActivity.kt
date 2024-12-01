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
import com.lowjunee.healthsafe.model.Medication


class MainActivity : ComponentActivity() {
    private val firestoreHelper = FirestoreHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        //firestoreHelper.addTestPastVisits()

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
    var selectedMedication by remember { mutableStateOf<Map<String, Any>?>(null) }

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
                        onAddPastVisit = { selectedScreen = "add_past_visit" } // Navigate to AddPastVisitsScreen
                    )
                    "add_past_visit" -> AddPastVisitsScreen(
                        onBackClick = { selectedScreen = "past_visits" },
                        onSaveClick = { pastVisit ->
                            firestoreHelper.addPastVisit(
                                pastVisit = pastVisit,
                                onSuccess = {
                                    println("Past visit added successfully!")
                                    selectedScreen = "past_visits" // Navigate back to PastVisitsScreen
                                },
                                onFailure = { e ->
                                    println("Error adding past visit: ${e.message}")
                                }
                            )
                        }
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
                                "Medications" -> selectedScreen = "medications"
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
                    "medications" -> MedicationsScreen(
                        onBackClick = { selectedScreen = "home" },
                        onAddMedicationClick = { selectedScreen = "add_medication" },
                        onEditMedicationClick = { medication ->
                            selectedMedication = medication
                            selectedScreen = "edit_medication"
                        }
                    )
                    "add_medication" -> AddMedicationScreen(
                        onBackClick = { selectedScreen = "medications" },
                        onSaveMedicationClick = { id, name, notes, dosage, interval, time, notify ->
                            val medication = Medication(
                                id = id, // Pass the generated ID
                                userId = "user123",
                                name = name,
                                notes = notes,
                                dosage = dosage,
                                interval = interval,
                                time = time.toString(),
                                notify = notify
                            )
                            firestoreHelper.addMedication(medication) {
                                println("Medication saved successfully")
                                selectedScreen = "medications"
                            }
                        }
                    )
                    "edit_medication" -> EditMedicationScreen(
                        medication = selectedMedication ?: emptyMap(),
                        onBackClick = { selectedScreen = "medications" },
                        onSaveMedicationClick = { name, notes, dosage, interval, time, notify, onComplete ->
                            if (selectedMedication != null) {
                                firestoreHelper.updateMedication(
                                    medicationId = selectedMedication!!["id"] as String, // Use the ID to update
                                    updatedData = mapOf(
                                        "name" to name,
                                        "notes" to notes,
                                        "dosage" to dosage,
                                        "interval" to interval,
                                        "time" to time,
                                        "notify" to notify
                                    ),
                                    onSuccess = {
                                        println("Medication updated successfully")
                                        onComplete() // Invoke the completion callback
                                        selectedScreen = "medications" // Navigate back to MedicationsScreen
                                    },
                                    onFailure = { exception ->
                                        println("Error updating medication: ${exception.message}")
                                    }
                                )
                            }
                        }
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
