package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data model for the tip
data class Tip(val title: String, val description: String)

@Composable
fun TipsScreen() {
    // Sample tips data
    val tips = listOf(
        Tip(
            title = "Stay Hydrated",
            description = "Drinking enough water is essential for maintaining good health and staying energized throughout the day."
        ),
        Tip(
            title = "Exercise Regularly",
            description = "Regular physical activity improves circulation, boosts mood, and helps to maintain a healthy weight."
        ),
        Tip(
            title = "Get Enough Sleep",
            description = "A good night’s sleep is vital for cognitive function, emotional health, and physical recovery."
        ),
        Tip(
            title = "Eat a Balanced Diet",
            description = "Including a variety of foods in your diet helps provide all the necessary nutrients to keep your body strong."
        ),
        Tip(
            title = "Practice Mental Wellness",
            description = "Take time each day for relaxation or meditation to manage stress and improve mental clarity."
        )
    )

    // Page layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 64.dp)
    ) {
        // Title of the page
        Text(
            text = "Health Tips",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // List of tips using LazyColumn
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(tips) { tip ->
                TipCard(tip)
            }
        }
    }
}

// Composable for displaying each tip in a Card
@Composable
fun TipCard(tip: Tip) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Light blue background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = tip.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tip.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
