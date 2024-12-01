package com.lowjunee.healthsafe.model

data class Medication(
    val id: String = "", // Add this to store the unique ID
    val userId: String = "",
    val name: String = "",
    val notes: String = "",
    val dosage: String = "",
    val interval: String = "",
    val time: String = "",
    val notify: Boolean = false
)



/* EXAMPLE USAGE
val medication = Medication(
    id = "med1",
    name = "Paracetamol",
    dosage = "500mg",
    interval = "Daily",
    time = "08:30",
    notify = true,
    notes = "Take with water."
)
 */