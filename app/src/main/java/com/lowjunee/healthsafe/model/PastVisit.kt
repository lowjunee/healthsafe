package com.lowjunee.healthsafe.model

import com.google.firebase.Timestamp

data class PastVisit(
    val id: String = "",
    val userId: String = "", // ID of the user who had the visit
    val visitPlace: String = "", // The place of the visit (e.g., hospital, clinic)
    val visitDate: String = "", // Must follow "yyyy-MM-dd HH:mm" format
    val doctorNotes: String = "", // Notes from the doctor
    val dietaryInstructions: String = "", // Dietary instructions given during the visit
    val postOperativeInstructions: String = "", // Post-operative care instructions
    val medicationNotes: String = "" // Any specific notes regarding medications prescribed

)


