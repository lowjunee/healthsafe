package com.lowjunee.healthsafe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.ui.components.HomeButton
import com.lowjunee.healthsafe.ui.theme.PrimaryColor

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    onNavigate(tab)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        HomeButton(
                            text = "My Metrics",
                            width = 150.dp,
                            height = 80.dp
                        ) { onNavigate("metrics") }

                        HomeButton(
                            text = "Medications",
                            width = 150.dp,
                            height = 80.dp
                        ) { onNavigate("medications") }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        HomeButton(
                            text = "Past Visits",
                            width = 150.dp,
                            height = 80.dp
                        ) { onNavigate("past_visits") }

                        HomeButton(
                            text = "SOS",
                            width = 150.dp,
                            height = 80.dp
                        ) { onNavigate("sos") }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // "Release Data Through QR" Button (wider)
                    HomeButton(
                        text = "Release Data Through QR",
                        width = 300.dp,
                        height = 80.dp
                    ) { onNavigate("qr_release") }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == "home",
            onClick = { onTabSelected("home") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = selectedTab == "tips",
            onClick = { onTabSelected("tips") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Tips"
                )
            },
            label = { Text("Tips") }
        )

        NavigationBarItem(
            selected = selectedTab == "profile",
            onClick = { onTabSelected("profile") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile") }
        )

        NavigationBarItem(
            selected = selectedTab == "settings",
            onClick = { onTabSelected("settings") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            },
            label = { Text("Settings") }
        )
    }
}
