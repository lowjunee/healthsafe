package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.R
import com.lowjunee.healthsafe.ui.theme.BottomNavigationBar
import com.lowjunee.healthsafe.ui.theme.HomeButton
import com.lowjunee.healthsafe.ui.theme.PrimaryColor

@Composable
fun HomeScreen(
    userName: String, // Pass the user's name dynamically
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
                // Profile Picture
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Greeting and Name
                Column {
                    Text(
                        text = "Hi, Welcome Back",
                        color = PrimaryColor,
                        fontSize = 14.sp
                    )
                    Text(
                        text = userName,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

            }

            Spacer(modifier = Modifier.height(20.dp))

            // Grid Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // First Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically // Center icons vertically
                ) {
                    FeatureItem(
                        imageRes = R.drawable.metrics,
                        label = "My Metrics",
                        onClick = { onNavigate("metrics") },
                        size = 80.dp
                    )
                    FeatureItem(
                        imageRes = R.drawable.pill,
                        label = "Medications",
                        onClick = { onNavigate("medications") },
                        size = 80.dp
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                // Second Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically // Center icons vertically
                ) {
                    FeatureItem(
                        imageRes = R.drawable.clinic,
                        label = "Past Visits",
                        onClick = { onNavigate("past_visits") },
                        size = 80.dp
                    )
                    FeatureItem(
                        imageRes = R.drawable.appointment_logo,
                        label = "Appointment",
                        onClick = { onNavigate("appointments") }, // Updated navigation to appointments
                        size = 80.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Release Data Through QR Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeButton(
                    text = "Release Data Through QR",
                    iconId = R.drawable.qr_code,
                    color = PrimaryColor
                ) { onNavigate("qr_release") }
            }
        }
    }
}


@Composable
fun FeatureItem(
    imageRes: Int,
    label: String,
    onClick: () -> Unit,
    size: Dp
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier.size(size),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
