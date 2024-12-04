import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import com.lowjunee.healthsafe.ui.theme.PrimaryColor
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppointmentScreen(
    onBackClick: () -> Unit,
    doctorName: String, // Doctor's name
    clinicName: String, // Clinic name
    onSaveAppointmentClick: (String, String, LocalTime, LocalDate) -> Unit // Removed doctorId
) {
    var reason by remember { mutableStateOf("") }
    var visitDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Appointment", fontSize = 20.sp, color = Color.White) },
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
                            if (reason.isNotEmpty() && visitDate.isNotEmpty()) {
                                val dateParts = visitDate.split(" ")
                                val date = dateParts[0].split("-").let { parts ->
                                    LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                                }

                                onSaveAppointmentClick(reason, clinicName, selectedTime, date)
                            } else {
                                println("Reason and visit date cannot be empty")
                            }
                        }
                    ) {
                        Text("Done", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = PrimaryColor)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Display selected doctor and clinic
            Text(text = "Doctor: $doctorName", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
            Text(text = "Clinic: $clinicName", style = MaterialTheme.typography.bodyLarge, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            // Reason for Visit
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Reason for Visit") },
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Visit Date
            OutlinedTextField(
                value = visitDate,
                onValueChange = { visitDate = it },
                label = { Text("Visit Date (e.g., 2024-12-10)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Time Picker
            Text("Pick Time", style = MaterialTheme.typography.bodyLarge)
            TimePicker(
                selectedTime = selectedTime,
                onTimeChange = { selectedTime = it }
            )
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
            LazyColumn(
                modifier = Modifier.height(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(24) { h ->
                    Text(
                        text = h.toString().padStart(2, '0'),
                        modifier = Modifier.clickable {
                            hour = h
                            onTimeChange(LocalTime.of(hour, minute))
                        },
                        color = if (h == hour) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Minute Picker
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Minute")
            LazyColumn(
                modifier = Modifier.height(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(60) { m ->
                    Text(
                        text = m.toString().padStart(2, '0'),
                        modifier = Modifier.clickable {
                            minute = m
                            onTimeChange(LocalTime.of(hour, minute))
                        },
                        color = if (m == minute) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
