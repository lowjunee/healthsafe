package com.lowjunee.healthsafe.ui.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScreen(
    onBackClick: () -> Unit,
    onGenerateQRClick: (Boolean, Boolean, Boolean) -> Unit, // Callback for generating QR
    onNavigate: (String) -> Unit // Navigation callback to QR result screen
) {
    var includeHealthMetrics by remember { mutableStateOf(false) }
    var includeMedications by remember { mutableStateOf(false) }
    var includePastVisits by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Release Data Through QR", color = Color.White, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Toggles for selecting data to include in QR code
            QRDataOptionCard(
                title = "Health Metrics",
                isChecked = includeHealthMetrics,
                onCheckedChange = { includeHealthMetrics = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            QRDataOptionCard(
                title = "Medications",
                isChecked = includeMedications,
                onCheckedChange = { includeMedications = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            QRDataOptionCard(
                title = "Past Visits",
                isChecked = includePastVisits,
                onCheckedChange = { includePastVisits = it }
            )


            // Generate QR Button
            Button(
                onClick = {
                    // Generate the QR based on selected toggles
                    onGenerateQRClick(includeHealthMetrics, includeMedications, includePastVisits)
                    // Navigate to QR result screen
                    onNavigate("qr_result_screen")  // Navigate to QR result screen
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Generate QR", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun QRDataOptionCard(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB3E5FC)) // Use default design
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Black
            )
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = PrimaryColor)
            )
        }
    }
}
