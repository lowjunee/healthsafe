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
fun FlightsClimbedMetricsScreen(
    firestoreHelper: FirestoreHelper,
    userId: String,
    onBackClick: () -> Unit
) {
    var flightsClimbedMetrics by remember { mutableStateOf<List<Metric>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Fetch flights climbed data from Firestore
    LaunchedEffect(Unit) {
        firestoreHelper.getFlightsClimbedMetrics(
            userId = userId,
            onSuccess = { metrics ->
                isLoading = false
                flightsClimbedMetrics = metrics.sortedByDescending { it.timestamp } // Sort by most recent
            },
            onFailure = { error ->
                isLoading = false
                errorMessage = error
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flights Climbed Data") },
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
                flightsClimbedMetrics.isEmpty() -> {
                    Text(
                        text = "No flights climbed data found. Please Insert Dummy Data at Profile Page.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        items(flightsClimbedMetrics) { metric ->
                            FlightsClimbedItem(metric)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlightsClimbedItem(metric: Metric) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDCE775)) // Light green for flights climbed
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${metric.flightsClimbed} Flights",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = metric.timestamp,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
