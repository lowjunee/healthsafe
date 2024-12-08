package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lowjunee.healthsafe.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationsScreen(
    onBackClick: () -> Unit,
    onAddMedicationClick: () -> Unit,
    onEditMedicationClick: (Map<String, Any>) -> Unit // Pass medication details to the edit screen
) {
    var medications by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // Fetch medications from Firestore
    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            firestore.collection("medications")
                .whereEqualTo("userId", userId) // Filter by the current user's ID
                .get()
                .addOnSuccessListener { result ->
                    medications = result.map { it.data }
                }
                .addOnFailureListener { e ->
                    println("Error fetching medications: ${e.message}")
                }
        } else {
            println("User ID is empty. Cannot fetch medications.")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Medications", color = Color.White, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onAddMedicationClick) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Medication",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = PrimaryColor)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp)
        ) {
            if (medications.isEmpty()) {
                // Show empty state message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No medications added yet. Use the '+' button to add a new medication.",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            } else {
                // List of medications
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    medications.forEach { medication ->
                        val name = medication["name"] as? String ?: "Unnamed Medication"
                        val dosage = medication["dosage"] as? String ?: "No dosage"
                        val notes = medication["notes"] as? String ?: "No notes"
                        val time = medication["time"] as? String ?: "No time"
                        val interval = medication["interval"] as? String ?: "No interval"

                        MedicationCard(
                            name = name,
                            dosage = dosage,
                            notes = notes,
                            time = time,
                            interval = interval,
                            onClick = { onEditMedicationClick(medication) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationCard(
    name: String,
    dosage: String,
    notes: String,
    time: String,
    interval: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }, // Handle card click
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB3E5FC))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Left side: Medication name, dosage, and notes
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = dosage,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Notes: $notes",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Right side: Time and interval
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = time,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = interval,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

