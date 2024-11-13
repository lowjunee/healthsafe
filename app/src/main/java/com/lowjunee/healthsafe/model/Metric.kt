package com.lowjunee.healthsafe.model

data class Metric(
    val userId: String = "", // User ID to identify the user
    val heartRate: Int = 0, // Heart rate in bpm
    val bloodPressure: String = "", // Blood pressure in "systolic/diastolic" format
    val ecg: String = "", // ECG reading or description
    val sleepStartTime: String = "", // Sleep start time in 24-hour format (e.g., "22:30")
    val sleepEndTime: String = "", // Sleep end time in 24-hour format (e.g., "06:30")
    val sleepDuration: Float = 0f, // Total sleep duration in hours
    val steps: Int = 0, // Total steps taken
    val caloriesBurned: Int = 0, // Total calories burned
    val flightsClimbed: Int = 0, // Total flights of stairs climbed
    val timestamp: String = "" // Timestamp in "yyyy-MM-dd HH:mm" format (e.g., "2024-11-13 14:45")
)

/* EXAMPLE
val metric = Metric(
    userId = "user123",
    heartRate = 72,
    bloodPressure = "120/80",
    ecg = "Normal sinus rhythm",
    sleepStartTime = "22:30",
    sleepEndTime = "06:30",
    sleepDuration = 8.0f,
    steps = 10000,
    caloriesBurned = 500,
    flightsClimbed = 10,
    timestamp = "2024-11-13 14:45"
)
*/
