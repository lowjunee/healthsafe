package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen(
    onBackClick: () -> Unit,
    onSaveMedicationClick: (String, String, String, String, String, LocalTime, Boolean) -> Unit
) {
    var medicationName by remember { mutableStateOf("") }
    var medicationNotes by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("Daily") }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var notify by remember { mutableStateOf(false) }

    // Updated intervals list
    val intervals = listOf("Daily", "Twice-Daily", "Thrice-Daily", "Weekly", "Monthly")
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Medication", fontSize = 20.sp, color = Color.White) },
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
                            if (medicationName.isNotEmpty() && dosage.isNotEmpty()) {
                                val id = UUID.randomUUID().toString() // Generate a unique ID
                                onSaveMedicationClick(
                                    id, // Include the ID
                                    medicationName,
                                    medicationNotes,
                                    dosage,
                                    interval,
                                    selectedTime,
                                    notify
                                )
                            } else {
                                println("Medication name and dosage cannot be empty")
                            }
                        }
                    ) {
                        Text("Done", color = Color.White)
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
                .verticalScroll(scrollState)
                .padding(16.dp)
                .padding(bottom = 64.dp)
        ) {
            // Medication Name
            OutlinedTextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                label = { Text("Medication Name") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Notes
            OutlinedTextField(
                value = medicationNotes,
                onValueChange = { medicationNotes = it },
                label = { Text("Medication Notes e.g. After Meals") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dosage
            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage e.g. 5mg") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Interval (Radio Button Group)
            Text("Interval", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
            intervals.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { interval = option }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (option == interval),
                        onClick = { interval = option }
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Time Picker
            Text("Pick Time", style = MaterialTheme.typography.bodyLarge)
            TimePicker(
                selectedTime = selectedTime,
                onTimeChange = { selectedTime = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Notify Toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Notify", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = notify,
                    onCheckedChange = { notify = it }
                )
            }
        }
    }
}

@Composable
fun TimePicker(
    selectedTime: LocalTime,
    onTimeChange: (LocalTime) -> Unit
) {
    var hour by remember { mutableStateOf(selectedTime.hour) }
    var minute by remember { mutableStateOf(selectedTime.minute) }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hour Picker
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Hour")
            androidx.compose.foundation.lazy.LazyColumn(
                modifier = Modifier.height(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(24) { h ->
                    Text(
                        text = h.toString(),
                        modifier = Modifier.clickable { hour = h },
                        color = if (h == hour) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Minute Picker
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Minute")
            androidx.compose.foundation.lazy.LazyColumn(
                modifier = Modifier.height(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(60) { m ->
                    Text(
                        text = m.toString().padStart(2, '0'),
                        modifier = Modifier.clickable { minute = m },
                        color = if (m == minute) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

    onTimeChange(LocalTime.of(hour, minute))
}
