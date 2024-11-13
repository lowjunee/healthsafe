package com.lowjunee.healthsafe.data

import com.google.firebase.firestore.FirebaseFirestore
import com.lowjunee.healthsafe.model.Metric
import com.lowjunee.healthsafe.model.Medication
import com.lowjunee.healthsafe.model.PastVisit
import com.lowjunee.healthsafe.model.User

class FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    // Fetch Past Visits
    fun fetchPastVisits(onSuccess: (List<PastVisit>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("past_visits")
            .get()
            .addOnSuccessListener { documents ->
                val visits = documents.mapNotNull { it.toObject(PastVisit::class.java) }
                onSuccess(visits)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Upload Test Data
    fun addTestPastVisit() {
        val sampleVisit = PastVisit(
            userId = "testUser",
            visitPlace = "Hospital Kuala Lumpur",
            visitDate = "10 Aug 2024",
            doctorNotes = "Patient recovering well",
            dietaryInstructions = "Low salt diet",
            postOperativeInstructions = "No heavy lifting",
            medicationNotes = "Continue antibiotics for 7 days"
        )

        db.collection("past_visits")
            .add(sampleVisit)
            .addOnSuccessListener {
                println("Test data added successfully!")
            }
            .addOnFailureListener { e ->
                println("Failed to add test data: ${e.message}")
            }
    }
}
