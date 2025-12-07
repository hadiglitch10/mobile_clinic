package com.meditrack

data class Patient(
    val id: Long = 0L,
    val name: String,
    val age: Int? = null,
    val phone: String? = null,
    val email: String? = null,
    val medicalHistory: String? = null
)

data class Appointment(
    val id: Long = 0L,
    val patientId: Long,
    val dateTime: String, // simple ISO-like string for lab (e.g. 2025-12-01T10:00)
    val purpose: String
)
