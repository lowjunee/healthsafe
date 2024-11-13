package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.R
import com.lowjunee.healthsafe.model.PastVisit
import com.lowjunee.healthsafe.ui.theme.PrimaryColor

@Composable
fun PastVisitsScreen(
    pastVisits: List<PastVisit>,
    onBackClick: () -> Unit,
    onAddPastVisit: () -> Unit
) {
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
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "HealthSafe",
                color = PrimaryColor,
                fontSize = 28.sp,
                modifier = Modifier.weight(1f)
            )

            // Add Button
            IconButton(onClick = onAddPastVisit) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Past Visit",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title Section
        Text(
            text = "Past Visits",
            fontSize = 24.sp,
            color = PrimaryColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List of Past Visits
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            pastVisits.forEach { pastVisit ->
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
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Place: ${pastVisit.visitPlace}", fontSize = 18.sp, color = PrimaryColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${pastVisit.visitDate.toDate()}", fontSize = 14.sp, color = Color.Gray)
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
