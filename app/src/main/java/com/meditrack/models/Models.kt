package com.meditrack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val age: Int? = null,
    val phone: String? = null,
    val email: String? = null,
    val medicalHistory: String? = null
)

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val patientId: Long,
    val dateTime: String, // simple ISO-like string for lab (e.g. 2025-12-01T10:00)
    val purpose: String
)
