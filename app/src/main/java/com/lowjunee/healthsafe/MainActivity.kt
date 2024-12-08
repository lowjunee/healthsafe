package com.lowjunee.healthsafe

import AddAppointmentScreen
import AppointmentsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lowjunee.healthsafe.ui.theme.HealthSafeTheme
import com.lowjunee.healthsafe.ui.screen.*
import com.lowjunee.healthsafe.ui.theme.BottomNavigationBar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.lowjunee.healthsafe.data.FirestoreHelper
import com.lowjunee.healthsafe.model.Medication

class MainActivity : ComponentActivity() {
    private val firestoreHelper = FirestoreHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    var selectedMedication by remember { mutableStateOf<Map<String, Any>?>(null) }
    var selectedDoctorName by remember { mutableStateOf("") }
    var selectedClinicName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf<String?>(null) }
    var userId by remember { mutableStateOf<String?>(null) } // Dynamically update userId

    // Monitor the current user's authentication state
    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            userId = currentUser.uid
            firestoreHelper.getUserDetails(
                onSuccess = { userDetails ->
                    userName = userDetails["name"] ?: "Guest"
                    showLoginScreen = false
                },
                onFailure = {
                    userName = "Guest"
                    showLoginScreen = false
                }
            )
        } else {
            userName = null
            userId = null
            showLoginScreen = true
        }
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        when {
            showLoginScreen -> LoginScreen(
                onSignInClick = {
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (currentUser != null) {
                        userId = currentUser.uid
                        firestoreHelper.getUserDetails(
                            onSuccess = { userDetails ->
                                userName = userDetails["name"] ?: "Guest"
                                showLoginScreen = false
                            },
                            onFailure = {
                                userName = "Guest"
                                showLoginScreen = false
                            }
                        )
                    }
                },
                onRegisterClick = {
                    showLoginScreen = false
                    showSignUpScreen = true }
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
                    "home" -> HomeScreen(
                        userName = userName ?: "Guest",
                        onNavigate = { selectedScreen = it }
                    )
                    "appointments" -> AppointmentsScreen(
                        onBackClick = { selectedScreen = "home" },
                        onDoctorClick = { name, clinic ->
                            selectedDoctorName = name
                            selectedClinicName = clinic
                            selectedScreen = "add_appointment"
                        }
                    )
                    "add_appointment" -> AddAppointmentScreen(
                        onBackClick = { selectedScreen = "appointments" },
                        doctorName = selectedDoctorName,
                        clinicName = selectedClinicName,
                        onSaveAppointmentClick = { reason, clinicName, time, date ->
                            firestoreHelper.addAppointment(
                                reason = reason,
                                name = selectedDoctorName,
                                clinic = selectedClinicName,
                                time = time,
                                date = date,
                                onSuccess = {
                                    println("Appointment added successfully!")
                                    selectedScreen = "appointments"
                                },
                                onFailure = { e ->
                                    println("Error adding appointment: ${e.message}")
                                }
                            )
                        }
                    )
                    "past_visits" -> PastVisitsScreen(
                        onBackClick = { selectedScreen = "home" },
                        onAddPastVisit = { selectedScreen = "add_past_visit" }
                    )
                    "add_past_visit" -> AddPastVisitsScreen(
                        onBackClick = { selectedScreen = "past_visits" },
                        onSaveClick = { pastVisit ->
                            firestoreHelper.addPastVisit(
                                pastVisit = pastVisit,
                                onSuccess = {
                                    println("Past visit added successfully!")
                                    selectedScreen = "past_visits"
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
                    "heart_rate_metrics" -> userId?.let {
                        HeartRateMetricsScreen(
                            firestoreHelper = firestoreHelper,
                            userId = it,
                            onBackClick = { selectedScreen = "metrics" }
                        )
                    }
                    "blood_pressure_metrics" -> userId?.let {
                        BloodPressureMetricsScreen(
                            firestoreHelper = firestoreHelper,
                            userId = it,
                            onBackClick = { selectedScreen = "metrics" }
                        )
                    }
                    "ecg_metrics" -> userId?.let {
                        ECGMetricsScreen(
                            firestoreHelper = firestoreHelper,
                            userId = it,
                            onBackClick = { selectedScreen = "metrics" }
                        )
                    }
                    "steps_metrics" -> userId?.let {
                        StepsMetricsScreen(
                            firestoreHelper = firestoreHelper,
                            userId = it,
                            onBackClick = { selectedScreen = "metrics" }
                        )
                    }
                    "calories_burned_metrics" -> userId?.let {
                        CaloriesBurnedMetricsScreen(
                            firestoreHelper = firestoreHelper,
                            userId = it,
                            onBackClick = { selectedScreen = "metrics" }
                        )
                    }
                    "flights_climbed_metrics" -> userId?.let {
                        FlightsClimbedMetricsScreen(
                            firestoreHelper = firestoreHelper,
                            userId = it,
                            onBackClick = { selectedScreen = "metrics" }
                        )
                    }
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
                                id = id,
                                userId = userId ?: "",
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
                                    medicationId = selectedMedication!!["id"] as String,
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
                                        onComplete()
                                        selectedScreen = "medications"
                                    },
                                    onFailure = { exception ->
                                        println("Error updating medication: ${exception.message}")
                                    }
                                )
                            }
                        },
                        onDeleteMedicationClick = {
                            if (selectedMedication != null) {
                                firestoreHelper.deleteMedication(
                                    medicationId = selectedMedication!!["id"] as String,
                                    onSuccess = {
                                        println("Medication deleted successfully")
                                        selectedScreen = "medications"
                                    },
                                    onFailure = { exception ->
                                        println("Error deleting medication: ${exception.message}")
                                    }
                                )
                            }
                        }
                    )
                    "profile" -> ProfileScreen(
                        firestoreHelper = firestoreHelper,
                        userId = userId ?: "",
                        onLinkDataSuccess = { selectedScreen = "metrics" },
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            selectedScreen = "home"
                            showLoginScreen = true
                        }
                    )
                    "tips" -> TipsScreen()
                    "qr_release" -> QRScreen(
                        onBackClick = { selectedScreen = "home" },  // Go back to home screen
                        onGenerateQRClick = { includeHealthMetrics, includeMedications, includePastVisits ->
                            // You can handle the data selection logic here before generating the QR
                            // Pass the selected data flags to be encoded in the QR code
                        },
                        onNavigate = { selectedScreen = it }  // This handles the navigation
                    )
                    "qr_result_screen" -> QRResultScreen(
                        onBackClick = { selectedScreen = "qr_release"}
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
