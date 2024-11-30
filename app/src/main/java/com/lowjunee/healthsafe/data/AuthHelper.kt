package com.lowjunee.healthsafe.data

import com.google.firebase.auth.FirebaseAuth

class AuthHelper {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Sign Up Function
    fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Sign-up failed") }
    }

    // Login Function
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Login failed") }
    }

    // Logout Function
    fun logout(onComplete: () -> Unit) {
        auth.signOut()
        onComplete()
    }

    // Check if User is Logged In
    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
