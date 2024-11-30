package com.lowjunee.healthsafe.data

import com.google.firebase.firestore.FirebaseFirestore
import com.lowjunee.healthsafe.model.Metric
import com.lowjunee.healthsafe.model.PastVisit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FirestoreHelper {
    private val db = FirebaseFirestore.getInstance()

    // Fetch Metrics
    fun getMetrics(onSuccess: (List<Metric>) -> Unit) {
        db.collection("metrics")
            .get()
            .addOnSuccessListener { documents ->
                val metrics = documents.mapNotNull { document ->
                    try {
                        val metric = document.toObject(Metric::class.java)

                        // Ensure timestamp is treated as a String
                        val timestampField = document.get("timestamp")
                        if (timestampField is String) {
                            metric
                        } else if (timestampField is Long) {
                            val formattedTimestamp = SimpleDateFormat(
                                "yyyy-MM-dd HH:mm", Locale.getDefault()
                            ).format(Date(timestampField))
                            metric.copy(timestamp = formattedTimestamp)
                        } else {
                            null
                        }
                    } catch (e: Exception) {
                        println("Error deserializing metric: ${e.message}")
                        null
                    }
                }
                onSuccess(metrics)
            }
            .addOnFailureListener { exception ->
                println("Error fetching metrics: ${exception.message}")
                onSuccess(emptyList()) // Return an empty list on failure
            }
    }

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

    // Insert Dummy Data into Firestore
    fun insertDummyData(
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val dummyData = mapOf(
            "heart_rate" to listOf(
                Metric(userId = userId, heartRate = 72, timestamp = "2024-11-30 10:00"),
                Metric(userId = userId, heartRate = 78, timestamp = "2024-11-30 11:00"),
                Metric(userId = userId, heartRate = 85, timestamp = "2024-11-30 12:00"),
                Metric(userId = userId, heartRate = 88, timestamp = "2024-11-30 13:00"),
                Metric(userId = userId, heartRate = 76, timestamp = "2024-11-30 14:00"),
                Metric(userId = userId, heartRate = 90, timestamp = "2024-11-30 15:00"),
                Metric(userId = userId, heartRate = 82, timestamp = "2024-11-30 16:00"),
                Metric(userId = userId, heartRate = 75, timestamp = "2024-11-30 17:00"),
                Metric(userId = userId, heartRate = 79, timestamp = "2024-11-30 18:00"),
                Metric(userId = userId, heartRate = 86, timestamp = "2024-11-30 19:00"),
                Metric(userId = userId, heartRate = 88, timestamp = "2024-11-30 20:00"),
                Metric(userId = userId, heartRate = 81, timestamp = "2024-11-30 21:00")
            ),
            "steps" to listOf(
                Metric(userId = userId, steps = 150, timestamp = "2024-11-30 06:00"), // Early morning
                Metric(userId = userId, steps = 300, timestamp = "2024-11-30 07:00"), // Morning routine
                Metric(userId = userId, steps = 500, timestamp = "2024-11-30 08:00"), // Morning commute
                Metric(userId = userId, steps = 450, timestamp = "2024-11-30 09:00"), // Light office walking
                Metric(userId = userId, steps = 700, timestamp = "2024-11-30 10:00"), // Late morning errands
                Metric(userId = userId, steps = 1000, timestamp = "2024-11-30 11:00"), // Active work or shopping
                Metric(userId = userId, steps = 900, timestamp = "2024-11-30 12:00"), // Walking during lunch
                Metric(userId = userId, steps = 800, timestamp = "2024-11-30 13:00"), // Post-lunch movement
                Metric(userId = userId, steps = 600, timestamp = "2024-11-30 14:00"), // Afternoon activity
                Metric(userId = userId, steps = 500, timestamp = "2024-11-30 15:00"), // Light office walking
                Metric(userId = userId, steps = 400, timestamp = "2024-11-30 16:00"), // Reduced activity
                Metric(userId = userId, steps = 600, timestamp = "2024-11-30 17:00"), // Evening errands or walk
                Metric(userId = userId, steps = 800, timestamp = "2024-11-30 18:00"), // Post-dinner activity
                Metric(userId = userId, steps = 500, timestamp = "2024-11-30 19:00"), // Relaxed walking
                Metric(userId = userId, steps = 300, timestamp = "2024-11-30 20:00"), // Settling down
                Metric(userId = userId, steps = 200, timestamp = "2024-11-30 21:00"), // Minimal movement
                Metric(userId = userId, steps = 50, timestamp = "2024-11-30 22:00"),  // Pre-bedtime activity
                Metric(userId = userId, steps = 10, timestamp = "2024-11-30 23:00"),  // Near bed
            ),
            "calories_burned" to listOf(
                Metric(userId = userId, caloriesBurned = 200, timestamp = "2024-11-30 10:00"),
                Metric(userId = userId, caloriesBurned = 10, timestamp = "2024-11-30 11:00"),
                Metric(userId = userId, caloriesBurned = 0, timestamp = "2024-11-30 12:00"),
                Metric(userId = userId, caloriesBurned = 50, timestamp = "2024-11-30 13:00"),
                Metric(userId = userId, caloriesBurned = 30, timestamp = "2024-11-30 14:00"),
                Metric(userId = userId, caloriesBurned = 20, timestamp = "2024-11-30 15:00"),
                Metric(userId = userId, caloriesBurned = 45, timestamp = "2024-11-30 16:00"),
                Metric(userId = userId, caloriesBurned = 23, timestamp = "2024-11-30 17:00"),
                Metric(userId = userId, caloriesBurned = 15, timestamp = "2024-11-30 18:00"),
                Metric(userId = userId, caloriesBurned = 8, timestamp = "2024-11-30 19:00"),
                Metric(userId = userId, caloriesBurned = 5, timestamp = "2024-11-30 20:00"),
                Metric(userId = userId, caloriesBurned = 5, timestamp = "2024-11-30 21:00")
            ),
            "blood_pressure" to listOf(
                Metric(userId = userId, bloodPressure = "120/80", timestamp = "2024-11-30 10:00"),
                Metric(userId = userId, bloodPressure = "122/82", timestamp = "2024-11-30 11:00"),
                Metric(userId = userId, bloodPressure = "118/76", timestamp = "2024-11-30 12:00"),
                Metric(userId = userId, bloodPressure = "125/85", timestamp = "2024-11-30 13:00"),
                Metric(userId = userId, bloodPressure = "121/78", timestamp = "2024-11-30 14:00"),
                Metric(userId = userId, bloodPressure = "124/80", timestamp = "2024-11-30 15:00"),
                Metric(userId = userId, bloodPressure = "119/77", timestamp = "2024-11-30 16:00"),
                Metric(userId = userId, bloodPressure = "120/79", timestamp = "2024-11-30 17:00"),
                Metric(userId = userId, bloodPressure = "122/80", timestamp = "2024-11-30 18:00"),
                Metric(userId = userId, bloodPressure = "123/81", timestamp = "2024-11-30 19:00"),
                Metric(userId = userId, bloodPressure = "118/75", timestamp = "2024-11-30 20:00"),
                Metric(userId = userId, bloodPressure = "121/78", timestamp = "2024-11-30 21:00")
            ),
            "ecg" to listOf(
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 10:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 11:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 12:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 13:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 14:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 15:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 16:00"),
                Metric(userId = userId, ecg = "Sinus tachycardia", timestamp = "2024-11-30 17:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 18:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 19:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 20:00"),
                Metric(userId = userId, ecg = "Normal sinus rhythm", timestamp = "2024-11-30 21:00")
            ),
            "flights_climbed" to listOf(
                Metric(userId = userId, flightsClimbed = 0, timestamp = "2024-11-30 06:00"), // Morning routine
                Metric(userId = userId, flightsClimbed = 2, timestamp = "2024-11-30 07:00"), // Morning activity
                Metric(userId = userId, flightsClimbed = 0, timestamp = "2024-11-30 08:00"), // Morning commute
                Metric(userId = userId, flightsClimbed = 1, timestamp = "2024-11-30 09:00"), // Office stairs
                Metric(userId = userId, flightsClimbed = 3, timestamp = "2024-11-30 10:00"), // Errands
                Metric(userId = userId, flightsClimbed = 8, timestamp = "2024-11-30 11:00"), // Exercise or shopping
                Metric(userId = userId, flightsClimbed = 7, timestamp = "2024-11-30 12:00"), // Lunch break
                Metric(userId = userId, flightsClimbed = 5, timestamp = "2024-11-30 13:00"), // Afternoon activity
                Metric(userId = userId, flightsClimbed = 3, timestamp = "2024-11-30 14:00"), // Reduced activity
                Metric(userId = userId, flightsClimbed = 4, timestamp = "2024-11-30 15:00"), // Light activity
                Metric(userId = userId, flightsClimbed = 2, timestamp = "2024-11-30 16:00"), // Light office movement
                Metric(userId = userId, flightsClimbed = 6, timestamp = "2024-11-30 17:00"), // Evening walk or commute
                Metric(userId = userId, flightsClimbed = 2, timestamp = "2024-11-30 18:00"), // Post-dinner walk
                Metric(userId = userId, flightsClimbed = 4, timestamp = "2024-11-30 19:00"), // Evening routine
                Metric(userId = userId, flightsClimbed = 2, timestamp = "2024-11-30 20:00"), // Minimal movement
                Metric(userId = userId, flightsClimbed = 1, timestamp = "2024-11-30 21:00"), // Near bedtime
                Metric(userId = userId, flightsClimbed = 0, timestamp = "2024-11-30 22:00"), // Settling down
                Metric(userId = userId, flightsClimbed = 0, timestamp = "2024-11-30 23:00")  // Bedtime
            )
        )

        // Batch write dummy data to Firestore
        val batch = db.batch()
        dummyData.forEach { (metricType, metrics) ->
            metrics.forEach { metric ->
                val docRef = db.collection(metricType).document()
                batch.set(docRef, metric)
            }
        }

        batch.commit()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Error inserting dummy data")
            }
    }
    fun getHeartRateMetrics(
        userId: String,
        onSuccess: (List<Metric>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("heart_rate") // Specify the "heart_rate" collection
            .whereEqualTo("userId", userId) // Filter documents by userId
            .get()
            .addOnSuccessListener { documents ->
                // Map the documents to a list of Metric objects
                val heartRateMetrics = documents.mapNotNull { document ->
                    try {
                        document.toObject(Metric::class.java) // Convert Firestore document to Metric
                    } catch (e: Exception) {
                        println("Error converting document to Metric: ${e.message}")
                        null
                    }
                }
                onSuccess(heartRateMetrics) // Pass the resulting list to the success callback
            }
            .addOnFailureListener { exception ->
                val errorMessage = exception.message ?: "Error fetching heart rate metrics"
                println("Error fetching heart rate data: $errorMessage")
                onFailure(errorMessage) // Pass the error message to the failure callback
            }
    }

    fun getBloodPressureMetrics(
        userId: String,
        onSuccess: (List<Metric>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("blood_pressure") // Specify the "blood_pressure" collection
            .whereEqualTo("userId", userId) // Filter documents by userId
            .get()
            .addOnSuccessListener { documents ->
                val bloodPressureMetrics = documents.mapNotNull { document ->
                    try {
                        document.toObject(Metric::class.java)
                    } catch (e: Exception) {
                        println("Error converting document to Metric: ${e.message}")
                        null
                    }
                }
                onSuccess(bloodPressureMetrics) // Pass the resulting list to the success callback
            }
            .addOnFailureListener { exception ->
                val errorMessage = exception.message ?: "Error fetching blood pressure metrics"
                println("Error fetching blood pressure metrics: $errorMessage")
                onFailure(errorMessage) // Pass the error message to the failure callback
            }
    }

    fun getECGMetrics(
        userId: String,
        onSuccess: (List<Metric>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("ecg")
            .whereEqualTo("userId", userId) // Filter data for the specific user
            .get()
            .addOnSuccessListener { documents ->
                val metrics = documents.mapNotNull { document ->
                    try {
                        document.toObject(Metric::class.java)
                    } catch (e: Exception) {
                        println("Error deserializing ECG metric: ${e.message}")
                        null
                    }
                }
                onSuccess(metrics) // Return the fetched metrics
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Failed to fetch ECG metrics")
            }
    }

    fun getStepsMetrics(
        userId: String,
        onSuccess: (List<Metric>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("steps")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val stepsMetrics = documents.mapNotNull { it.toObject(Metric::class.java) }
                onSuccess(stepsMetrics)
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Error fetching steps metrics")
            }
    }

    fun getCaloriesBurnedMetrics(
        userId: String,
        onSuccess: (List<Metric>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("calories_burned") // Specify the correct collection
            .whereEqualTo("userId", userId) // Filter by userId
            .get()
            .addOnSuccessListener { documents ->
                val caloriesBurnedMetrics = documents.mapNotNull { document ->
                    try {
                        document.toObject(Metric::class.java) // Convert Firestore document to Metric
                    } catch (e: Exception) {
                        println("Error converting document to Metric: ${e.message}")
                        null
                    }
                }
                onSuccess(caloriesBurnedMetrics) // Pass the fetched data
            }
            .addOnFailureListener { exception ->
                val errorMessage = exception.message ?: "Error fetching calories burned metrics"
                println("Error fetching calories burned data: $errorMessage")
                onFailure(errorMessage)
            }
    }

    fun getFlightsClimbedMetrics(
        userId: String,
        onSuccess: (List<Metric>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("flights_climbed") // Make sure the collection name matches your Firestore setup
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val flightsClimbedMetrics = documents.mapNotNull { document ->
                    try {
                        document.toObject(Metric::class.java)
                    } catch (e: Exception) {
                        println("Error converting document to Metric: ${e.message}")
                        null
                    }
                }
                onSuccess(flightsClimbedMetrics)
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Error fetching flights climbed metrics")
            }
    }





}




