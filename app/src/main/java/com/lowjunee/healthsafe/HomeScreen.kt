package com.lowjunee.healthsafe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.ui.components.HomeButton
import com.lowjunee.healthsafe.ui.theme.PrimaryColor
import com.lowjunee.healthsafe.ui.theme.WhiteColor

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Section with App Name
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "HealthSafe",
            color = PrimaryColor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Welcome Text
        Text(
            text = "Hello Sarah!",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Buttons Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // First row of buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    HomeButton(
                        text = "My Metrics",
                        width = 150.dp,
                        height = 50.dp
                    ) { onNavigate("metrics") }

                    HomeButton(
                        text = "Medications",
                        width = 150.dp,
                        height = 50.dp
                    ) { onNavigate("medications") }
                }

                // Second row of buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    HomeButton(
                        text = "Past Visits",
                        width = 150.dp,
                        height = 50.dp
                    ) { onNavigate("past_visits") }

                    HomeButton(
                        text = "SOS",
                        width = 150.dp,
                        height = 50.dp
                    ) { onNavigate("sos") }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // "Release Data Through QR" Button (wider)
                HomeButton(
                    text = "Release Data Through QR",
                    width = 300.dp,
                    height = 50.dp
                ) { onNavigate("qr_release") }

                Spacer(modifier = Modifier.height(16.dp))

                // Log Out Button
                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Log Out",
                        fontSize = 16.sp,
                        color = WhiteColor
                    )
                }
            }
        }
    }
}
