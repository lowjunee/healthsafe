package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.R
import com.lowjunee.healthsafe.model.PastVisit
import com.lowjunee.healthsafe.ui.theme.PrimaryColor
import com.google.firebase.firestore.FirebaseFirestore
import com.lowjunee.healthsafe.data.FirestoreHelper

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
                pastVisits = fetchedVisits
            },
            onFailure = { e ->
                println("Failed to fetch past visits: ${e.message}")
            }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top Section with Back Button and Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = PrimaryColor
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Past Visits",
                color = PrimaryColor,
                fontSize = 28.sp,
                modifier = Modifier.weight(1f)
            )

            // Add Button
            IconButton(onClick = onAddPastVisit) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Past Visit",
                    tint = PrimaryColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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

@Composable
fun PastVisitCard(pastVisit: PastVisit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Place: ${pastVisit.visitPlace}", fontSize = 18.sp, color = PrimaryColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${pastVisit.visitDate}", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Doctor's Notes: ${pastVisit.doctorNotes}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Dietary Instructions: ${pastVisit.dietaryInstructions}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Post-Op Instructions: ${pastVisit.postOperativeInstructions}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Medication Notes: ${pastVisit.medicationNotes}", fontSize = 14.sp)
        }
    }
}
