package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.model.PastVisit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPastVisitsScreen(
    onBackClick: () -> Unit,
    onSaveClick: (PastVisit) -> Unit
) {
    var visitPlace by remember { mutableStateOf("") }
    var visitDate by remember { mutableStateOf("") }
    var doctorNotes by remember { mutableStateOf("") }
    var dietaryInstructions by remember { mutableStateOf("") }
    var postOperativeInstructions by remember { mutableStateOf("") }
    var medicationNotes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Past Visit", fontSize = 20.sp, color = Color.White) },
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
                    TextButton(
                        onClick = {
                            if (visitPlace.isNotEmpty() && visitDate.isNotEmpty()) {
                                val pastVisit = PastVisit(
                                    userId = "user123", // Replace with the actual user ID
                                    visitPlace = visitPlace,
                                    visitDate = visitDate,
                                    doctorNotes = doctorNotes,
                                    dietaryInstructions = dietaryInstructions,
                                    postOperativeInstructions = postOperativeInstructions,
                                    medicationNotes = medicationNotes
                                )
                                onSaveClick(pastVisit)
                            } else {
                                println("Visit Place and Date are mandatory fields")
                            }
                        }
                    ) {
                        Text("Save", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Visit Place
            OutlinedTextField(
                value = visitPlace,
                onValueChange = { visitPlace = it },
                label = { Text("Visit Place") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black), // Black text
                modifier = Modifier.fillMaxWidth()
            )

            // Visit Date
            OutlinedTextField(
                value = visitDate,
                onValueChange = { visitDate = it },
                label = { Text("Visit Date (e.g., 2024-02-10)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                textStyle = LocalTextStyle.current.copy(color = Color.Black), // Black text
                modifier = Modifier.fillMaxWidth()
            )

            // Doctor Notes
            OutlinedTextField(
                value = doctorNotes,
                onValueChange = { doctorNotes = it },
                label = { Text("Doctor Notes") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black), // Black text
                modifier = Modifier.fillMaxWidth()
            )

            // Dietary Instructions
            OutlinedTextField(
                value = dietaryInstructions,
                onValueChange = { dietaryInstructions = it },
                label = { Text("Dietary Instructions") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black), // Black text
                modifier = Modifier.fillMaxWidth()
            )

            // Post-Operative Instructions
            OutlinedTextField(
                value = postOperativeInstructions,
                onValueChange = { postOperativeInstructions = it },
                label = { Text("Post-Operative Instructions") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black), // Black text
                modifier = Modifier.fillMaxWidth()
            )

            // Medication Notes
            OutlinedTextField(
                value = medicationNotes,
                onValueChange = { medicationNotes = it },
                label = { Text("Medication Notes") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black), // Black text
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
