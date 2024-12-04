package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lowjunee.healthsafe.data.FirestoreHelper
import com.lowjunee.healthsafe.model.Metric
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartRateMetricsScreen(
    firestoreHelper: FirestoreHelper,
    userId: String,
    onBackClick: () -> Unit
) {
    var heartRateMetrics by remember { mutableStateOf<List<Metric>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }

    // Fetch user details and then heart rate data
    LaunchedEffect(Unit) {
        println("Fetching user details to retrieve userId") // Debugging
        firestoreHelper.getUserDetails(
            onSuccess = { userDetails ->
                userId = userDetails["userId"] ?: ""
                if (userId.isEmpty()) {
                    println("UserId is empty!") // Debugging
                    isLoading = false
                    errorMessage = "User ID not found. Please log in."
                    return@getUserDetails
                }
                println("Fetched userId: $userId") // Debugging

                // Now fetch heart rate metrics using the userId
                firestoreHelper.getHeartRateMetrics(
                    userId = userId,
                    onSuccess = { metrics ->
                        println("Fetched heart rate metrics: $metrics") // Debugging
                        isLoading = false
                        heartRateMetrics = metrics.sortedByDescending { it.timestamp } // Sort by timestamp
                    },
                    onFailure = { error ->
                        println("Error fetching heart rate metrics: $error") // Debugging
                        isLoading = false
                        errorMessage = error
                    }
                )
            },
            onFailure = { error ->
                println("Error fetching user details: ${error.message}") // Debugging
                isLoading = false
                errorMessage = "Failed to fetch user details: ${error.message}"
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Heart Rate Data") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage.isNotEmpty() -> {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                heartRateMetrics.isEmpty() -> {
                    Text(
                        text = "No heart rate data found. Please Insert Dummy Data at Profile Page.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        items(heartRateMetrics) { metric ->
                            HeartRateItem(metric)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeartRateItem(metric: Metric) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFeea9a9)) // Set to gray or desired color
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${metric.heartRate} BPM",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${metric.timestamp}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
