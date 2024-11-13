package com.lowjunee.healthsafe.data

import com.google.firebase.firestore.FirebaseFirestore
import com.lowjunee.healthsafe.model.Metric
import com.lowjunee.healthsafe.model.Medication
import com.lowjunee.healthsafe.model.PastVisit
import com.lowjunee.healthsafe.model.User

class FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    // Add or update a user
    fun addUser(userId: String, user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Add a new metric
    fun addMetric(metric: Metric, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("metrics")
            .add(metric)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Add a new medication
    fun addMedication(medication: Medication, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("medications")
            .add(medication)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    // Add a new past visit
    fun addPastVisit(pastVisit: PastVisit, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("past_visits")
            .add(pastVisit)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
