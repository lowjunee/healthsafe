package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lowjunee.healthsafe.data.FirestoreHelper

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween, // Space components evenly
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Header
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.weight(1f)) // Push buttons to the bottom

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 64.dp), // Add bottom padding
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

            Spacer(modifier = Modifier.height(16.dp))

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
