package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.R
import com.lowjunee.healthsafe.ui.theme.BottomNavigationBar
import com.lowjunee.healthsafe.ui.theme.HomeButton
import com.lowjunee.healthsafe.ui.theme.Medication
import com.lowjunee.healthsafe.ui.theme.Metrics
import com.lowjunee.healthsafe.ui.theme.PrimaryColor

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = "home",
                onTabSelected = { tab -> onNavigate(tab) }
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Profile Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Hi, Welcome Back",
                        color = PrimaryColor,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Sarah Lim",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { onNavigate("notifications") }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = PrimaryColor
                    )
                }

                IconButton(onClick = { onNavigate("settings") }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = PrimaryColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Buttons Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // My Metrics Button
                HomeButton(
                    text = "My Metrics",
                    iconId = R.drawable.metrics,
                    color = Metrics
                ) { onNavigate("metrics") }

                // Medications Button
                HomeButton(
                    text = "Medications",
                    iconId = R.drawable.pill,
                    color = Medication
                ) { onNavigate("medications") }

                // Past Visits Button
                HomeButton(
                    text = "Past Visits",
                    iconId = R.drawable.clinic,
                    color = PrimaryColor
                ) { onNavigate("past_visits") }

                // Make an Appointment Button
                HomeButton(
                    text = "Make an Appointment",
                    iconId = R.drawable.clinic,
                    color = PrimaryColor
                ) { onNavigate("appt") }

                // Release Data Through QR Button
                HomeButton(
                    text = "Release Data Through QR",
                    iconId = R.drawable.qr_code,
                    color = PrimaryColor
                ) { onNavigate("qr_release") }
            }
        }
    }
}
