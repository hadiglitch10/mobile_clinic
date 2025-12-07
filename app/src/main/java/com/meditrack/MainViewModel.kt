package com.meditrack

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // App-level state (in-memory, survives configuration changes)
    private val _patients = mutableStateListOf<Patient>()
    val patients: List<Patient> get() = _patients

    private val _appointments = mutableStateListOf<Appointment>()
    val appointments: List<Appointment> get() = _appointments

    init {
        // seed sample data
        if (_patients.isEmpty()) {
            _patients.add(Patient(id = 1L, name = "John Doe", age = 29, phone = "0100000000", email = "john@example.com", medicalHistory = "None"))
            _appointments.add(Appointment(id = 1L, patientId = 1L, dateTime = "2025-12-10T10:00", purpose = "General Checkup"))
        }
    }

    fun addPatient(patient: Patient) {
        val nextId = (_patients.maxOfOrNull { it.id } ?: 0L) + 1L
        _patients.add(patient.copy(id = nextId))
    }

    fun updatePatient(patient: Patient) {
        val idx = _patients.indexOfFirst { it.id == patient.id }
        if (idx >= 0) {
            _patients[idx] = patient
        }
    }

    fun addAppointment(appointment: Appointment) {
        val nextId = (_appointments.maxOfOrNull { it.id } ?: 0L) + 1L
        _appointments.add(appointment.copy(id = nextId))
    }
}
