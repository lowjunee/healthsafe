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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.model.Medication
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMedicationScreen(
    medication: Map<String, Any>, // Pass the selected medication details
    onBackClick: () -> Unit,
    onSaveMedicationClick: (String, String, String, String, String, Boolean, () -> Unit) -> Unit,
    onDeleteMedicationClick: () -> Unit // Callback for deleting the medication
) {
    // Parsing and initializing state
    var medicationName by remember { mutableStateOf(medication["name"] as? String ?: "") }
    var medicationNotes by remember { mutableStateOf(medication["notes"] as? String ?: "") }
    var dosage by remember { mutableStateOf(medication["dosage"] as? String ?: "") }
    var interval by remember { mutableStateOf(medication["interval"] as? String ?: "Daily") }
    var selectedTime by remember { mutableStateOf(medication["time"] as? String ?: "00:00") }
    var notify by remember { mutableStateOf(medication["notify"] as? Boolean ?: false) }

    val intervals = listOf("Daily", "Twice-Daily", "Thrice-Daily", "Weekly", "Monthly")
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Medication", fontSize = 20.sp, color = Color.White) },
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
                    Row {
                        // Delete Button
                        IconButton(onClick = onDeleteMedicationClick) {
                            Icon(
                                painter = painterResource(id = com.lowjunee.healthsafe.R.drawable.ic_delete), // Add your trash icon drawable
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }

                        // Save Button
                        TextButton(
                            onClick = {
                                if (medicationName.isNotEmpty() && dosage.isNotEmpty()) {
                                    onSaveMedicationClick(
                                        medicationName,
                                        medicationNotes,
                                        dosage,
                                        interval,
                                        selectedTime,
                                        notify
                                    ) {
                                        onBackClick() // Navigate back to `MedicationsScreen`
                                    }
                                } else {
                                    println("Medication name and dosage cannot be empty")
                                }
                            }
                        ) {
                            Text("Save", color = Color.White)
                        }
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
                label = { Text("Medication Notes") },
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
    selectedTime: String,
    onTimeChange: (String) -> Unit
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    var hour by remember { mutableStateOf(LocalTime.parse(selectedTime, timeFormatter).hour) }
    var minute by remember { mutableStateOf(LocalTime.parse(selectedTime, timeFormatter).minute) }

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

    val updatedTime = LocalTime.of(hour, minute).format(timeFormatter)
    onTimeChange(updatedTime)
}


