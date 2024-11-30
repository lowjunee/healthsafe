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
fun BloodPressureMetricsScreen(
    firestoreHelper: FirestoreHelper,
    userId: String,
    onBackClick: () -> Unit
) {
    var bloodPressureMetrics by remember { mutableStateOf<List<Metric>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Fetch blood pressure data from Firestore
    LaunchedEffect(Unit) {
        firestoreHelper.getBloodPressureMetrics(
            userId = userId,
            onSuccess = { metrics ->
                isLoading = false
                bloodPressureMetrics = metrics // Update state with fetched data
                bloodPressureMetrics = metrics.sortedByDescending { it.timestamp }
            },
            onFailure = { error ->
                isLoading = false
                errorMessage = error // Update state with error message
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Blood Pressure Data") },
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
                bloodPressureMetrics.isEmpty() -> {
                    Text(
                        text = "No blood pressure data found. Please Insert Dummy Data at Profile Page.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        items(bloodPressureMetrics) { metric ->
                            BloodPressureItem(metric)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BloodPressureItem(metric: Metric) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFeea9a9)) // Set desired color
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${metric.bloodPressure} mmHg",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${metric.timestamp}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
