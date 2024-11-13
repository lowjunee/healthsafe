package com.lowjunee.healthsafe.model

import com.google.firebase.Timestamp

data class PastVisit(
    val id: String = "",
    val userId: String = "", // ID of the user who had the visit
    val visitPlace: String = "", // The place of the visit (e.g., hospital, clinic)
    val visitDate: Timestamp = Timestamp.now(), // Timestamp of the visit
    val doctorNotes: String = "", // Notes from the doctor
    val dietaryInstructions: String = "", // Dietary instructions given during the visit
    val postOperativeInstructions: String = "", // Post-operative care instructions
    val medicationNotes: String = "" // Any specific notes regarding medications prescribed

)

val samplePastVisitsList = listOf(
    PastVisit(
        id = "visit1",
        userId = "user123",
        visitPlace = "General Hospital",
        visitDate = Timestamp.now(),
        doctorNotes = "Patient advised to rest for a week due to minor surgery.",
        dietaryInstructions = "Avoid spicy and oily foods.",
        postOperativeInstructions = "Keep the wound dry and clean. Return for follow-up in a week.",
        medicationNotes = "Prescribed antibiotics for 5 days. Painkillers as needed."
    ),
    PastVisit(
        id = "visit2",
        userId = "user123",
        visitPlace = "City Clinic",
        visitDate = Timestamp.now(),
        doctorNotes = "Regular check-up. Blood pressure is normal.",
        dietaryInstructions = "Maintain a balanced diet with more fruits and vegetables.",
        postOperativeInstructions = "N/A",
        medicationNotes = "No medications prescribed."
    ),
    PastVisit(
        id = "visit3",
        userId = "user456",
        visitPlace = "Downtown Medical Center",
        visitDate = Timestamp.now(),
        doctorNotes = "Patient complained of chest pain. ECG showed normal sinus rhythm.",
        dietaryInstructions = "Reduce salt intake. Avoid caffeine.",
        postOperativeInstructions = "N/A",
        medicationNotes = "Prescribed low-dose aspirin."
    )
)
