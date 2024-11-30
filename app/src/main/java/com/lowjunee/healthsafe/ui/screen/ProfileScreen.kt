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
    onLinkDataSuccess: () -> Unit // Callback when data linking succeeds
) {
    var isLoading by remember { mutableStateOf(false) }
    var isLinked by remember { mutableStateOf(false) } // Tracks if data is linked
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Header
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

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
                    text = if (isLinked) "Mobile Health Data Already Linked" else "Click to Insert Dummy Data"
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
    }
}
