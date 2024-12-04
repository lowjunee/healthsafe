package com.lowjunee.healthsafe.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthHelper {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Sign Up Function
    fun signUp(
        email: String,
        password: String,
        name: String, // Added name as a parameter
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    val user = hashMapOf(
                        "userId" to userId,
                        "name" to name,
                        "email" to email
                    )
                    // Save the user's details in Firestore
                    db.collection("users")
                        .document(userId)
                        .set(user)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e ->
                            onFailure("Sign-up successful but failed to save user details: ${e.message}")
                        }
                } else {
                    onFailure("Sign-up successful but failed to get user ID")
                }
            }
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

    // Fetch User Details from Firestore
    fun fetchUserDetails(
        userId: String,
        onSuccess: (Map<String, Any>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    onSuccess(document.data ?: emptyMap())
                } else {
                    onFailure("User not found")
                }
            }
            .addOnFailureListener { e ->
                onFailure("Error fetching user details: ${e.message}")
            }
    }
}
