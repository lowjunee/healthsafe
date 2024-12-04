package com.lowjunee.healthsafe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.data.AuthHelper // Import AuthHelper
import com.lowjunee.healthsafe.ui.theme.PrimaryColor
import com.lowjunee.healthsafe.ui.theme.WhiteColor

@Composable
fun LoginScreen(
    onSignInClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    // State variables for email, password, loading, and error message
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) } // Loading state
    val authHelper = AuthHelper() // Initialize AuthHelper

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Title
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "HealthSafe",
            color = WhiteColor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Login Form Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // "Sign In" Title
                Text(
                    text = "Sign In",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Email Input Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black), // Change input text color to black
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = email.isEmpty() && errorMessage.isNotEmpty()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Password Input Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black), // Change input text color to black
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = password.isEmpty() && errorMessage.isNotEmpty()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Error Message Display
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // "Sign In" Button
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
                } else {
                    Button(
                        onClick = {
                            if (email.isEmpty() || password.isEmpty()) {
                                errorMessage = "Please enter both email and password"
                            } else {
                                isLoading = true
                                errorMessage = ""
                                authHelper.login(
                                    email = email,
                                    password = password,
                                    onSuccess = {
                                        isLoading = false
                                        onSignInClick() // Navigate to the next screen
                                    },
                                    onFailure = { error ->
                                        isLoading = false
                                        errorMessage = error
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Sign In",
                            color = WhiteColor,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Arrow Icon",
                            tint = WhiteColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // "Register Now" Text
                Text(
                    text = "Register Now",
                    color = Color(0xFF1565C0),
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }
        }
    }
}

