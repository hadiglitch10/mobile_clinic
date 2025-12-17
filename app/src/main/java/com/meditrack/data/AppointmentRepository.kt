package com.meditrack.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.meditrack.data.Daos.AppointmentDao
import com.meditrack.data.entities.Appointment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AppointmentRepository(
    private val appointmentDao: AppointmentDao,
    private val firestore: FirebaseFirestore
) {
    private val appointmentsRef = firestore.collection("appointments")
    private var snapshotListener: ListenerRegistration? = null

    init {
        startSync()
    }

    private fun startSync() {
        snapshotListener = appointmentsRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("AppointmentRepo", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val appointments = snapshot.toObjects(Appointment::class.java)
                    appointmentDao.clearAll()
                    appointments.forEach { appointmentDao.insertAppointment(it) }
                }
            }
        }
    }

    fun getAllAppointments(): Flow<List<Appointment>> = appointmentDao.getAllAppointments()
    
    fun getAppointmentsForPatient(patientId: String): Flow<List<Appointment>> = appointmentDao.getAppointmentsForPatient(patientId)

    suspend fun addAppointment(appointment: Appointment) {
        val doc = if (appointment.id.isBlank()) appointmentsRef.document() else appointmentsRef.document(appointment.id)
        val appointmentWithId = appointment.copy(id = doc.id)
        doc.set(appointmentWithId).await()
    }

    suspend fun updateAppointment(appointment: Appointment) {
        appointmentsRef.document(appointment.id).set(appointment).await()
    }

    suspend fun deleteAppointment(appointmentId: String) {
        appointmentsRef.document(appointmentId).delete().await()
    }

    fun stopSync() {
        snapshotListener?.remove()
    }
}
