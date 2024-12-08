package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lowjunee.healthsafe.data.FirestoreHelper
import com.lowjunee.healthsafe.R // Replace with your actual R file path

@Composable
fun ProfileScreen(
    firestoreHelper: FirestoreHelper,
    userId: String, // Pass the current user's ID
    onLinkDataSuccess: () -> Unit, // Callback when data linking succeeds
    onLogout: () -> Unit // Callback when the user logs out
) {
    var isLoading by remember { mutableStateOf(false) }
    var isLinked by remember { mutableStateOf(false) } // Tracks if data is linked
    var errorMessage by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }

    // Fetch user details
    LaunchedEffect(Unit) {
        firestoreHelper.getUserDetails(
            onSuccess = { userDetails ->
                userName = userDetails["name"] ?: "Guest"
                userId = userDetails["userId"] ?: "guest_username"
            },
            onFailure = {
                userName = "Guest"
                userId = "guest_username"
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween, // Space components evenly
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture Placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile), // Replace with your placeholder image
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Name
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Username
            Text(
                text = "userId: $userId",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Push buttons to the bottom

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // "Link to Mobile Health Data" or "Mobile Health Data Already Linked" Button
            Button(
                onClick = {
                    if (!isLinked) { // Only execute if not already linked
                        isLoading = true
                        errorMessage = ""

                        firestoreHelper.insertDummyData(
                            userId = userId,
                            onSuccess = {
                                isLoading = false
                                isLinked = true // Set linked state to true
                                onLinkDataSuccess()
                            },
                            onFailure = { error ->
                                isLoading = false
                                errorMessage = error
                            }
                        )
                    }
                },
                enabled = !isLoading && !isLinked, // Disable button if loading or already linked
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = if (isLinked) "Mobile Health Data Already Linked" else "Click to Insert Dummy Metrics Data"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display error message if operation fails
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(0.dp))

            // Logout Button
            Button(
                onClick = { onLogout() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Logout", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}
