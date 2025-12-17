package com.meditrack.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = false)
    @DocumentId
    val id: String = "",
    val patientId: String = "",
    val doctorName: String = "",
    val dateTime: String = "",
    val purpose: String = "",
    val status: String = "Scheduled" // Scheduled, Completed, Cancelled
)
