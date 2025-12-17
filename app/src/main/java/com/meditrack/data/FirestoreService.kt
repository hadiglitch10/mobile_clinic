package com.meditrack.data

import com.google.firebase.firestore.FirebaseFirestore
import com.meditrack.data.entities.Appointment
import com.meditrack.data.entities.Patient
import kotlinx.coroutines.tasks.await

class FirestoreService {
    private val db = FirebaseFirestore.getInstance()
    private val patientsRef = db.collection("patients")
    private val appointmentsRef = db.collection("appointments")

    // Patients
    suspend fun getPatients(): List<Patient> {
        return try {
            patientsRef.get().await().toObjects(Patient::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addPatient(patient: Patient): Boolean {
        return try {
            val doc = if (patient.id.isBlank()) patientsRef.document() else patientsRef.document(patient.id)
            val patientWithId = patient.copy(id = doc.id)
            doc.set(patientWithId).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deletePatient(patientId: String): Boolean {
        return try {
            patientsRef.document(patientId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Appointments
    suspend fun getAppointments(): List<Appointment> {
        return try {
            appointmentsRef.get().await().toObjects(Appointment::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addAppointment(appointment: Appointment): Boolean {
        return try {
            val doc = if (appointment.id.isBlank()) appointmentsRef.document() else appointmentsRef.document(appointment.id)
            val appointmentWithId = appointment.copy(id = doc.id)
            doc.set(appointmentWithId).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteAppointment(appointmentId: String): Boolean {
        return try {
            appointmentsRef.document(appointmentId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
