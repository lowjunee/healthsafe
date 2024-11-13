package com.lowjunee.healthsafe.model

data class Medication(
    val userId: String = "",
    val name: String = "",
    val dosage: String = "",
    val interval: String = "", // Choices: "Daily", "Twice-Daily", "Thrice-Daily", "Weekly", "Monthly"
    val time: String = "", // Time in HH:mm format (e.g., "08:30")
    val notify: Boolean = false, // Whether to send a notification
    val notes: String = ""
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