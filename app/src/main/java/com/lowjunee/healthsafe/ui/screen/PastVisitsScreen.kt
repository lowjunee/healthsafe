package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.model.PastVisit
import com.lowjunee.healthsafe.ui.theme.PrimaryColor
import com.lowjunee.healthsafe.data.FirestoreHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastVisitsScreen(
    onBackClick: () -> Unit,
    onAddPastVisit: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var pastVisits by remember { mutableStateOf(listOf<PastVisit>()) }

    // Fetch data from Firestore
    LaunchedEffect(Unit) {
        FirestoreHelper().fetchPastVisits(
            onSuccess = { fetchedVisits ->
                pastVisits = fetchedVisits.sortedByDescending { visit ->
                    try {
                        // Parse `visitDate` to a comparable format
                        java.time.LocalDateTime.parse(
                            visit.visitDate,
                            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        )
                    } catch (e: Exception) {
                        java.time.LocalDateTime.MIN // Assign the earliest possible time if parsing fails
                    }
                }
            },
            onFailure = { e ->
                println("Failed to fetch past visits: ${e.message}")
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Past Visits", color = Color.White, fontSize = 20.sp) },
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
                    IconButton(onClick = onAddPastVisit) {
                        Icon(
                            painter = painterResource(id = com.lowjunee.healthsafe.R.drawable.ic_add),
                            contentDescription = "Add Past Visit",
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
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Filtered List of Past Visits
            val filteredVisits = pastVisits.filter {
                it.visitPlace.contains(searchQuery, ignoreCase = true)
            }

            // List of Past Visits
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                filteredVisits.forEach { pastVisit ->
                    PastVisitCard(pastVisit)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun PastVisitCard(pastVisit: PastVisit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)) // Gray background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Place: ${pastVisit.visitPlace}",
                fontSize = 18.sp,
                color = PrimaryColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Date: ${pastVisit.visitDate}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Doctor's Notes: ${pastVisit.doctorNotes}",
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Dietary Instructions: ${pastVisit.dietaryInstructions}",
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Post-Op Instructions: ${pastVisit.postOperativeInstructions}",
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Medication Notes: ${pastVisit.medicationNotes}",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}
