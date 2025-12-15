package com.meditrack.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.meditrack.Patient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface PatientRepository {
    fun getPatientsFlow(): Flow<List<Patient>>
    suspend fun addPatient(patient: Patient)
    suspend fun updatePatient(patient: Patient)
}

class PatientRepositoryImpl : PatientRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val patientsCollection = firestore.collection("patients")

    override fun getPatientsFlow(): Flow<List<Patient>> = callbackFlow {
        val listener: ListenerRegistration = patientsCollection
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val patients = snapshot.toObjects(Patient::class.java)
                    trySend(patients)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addPatient(patient: Patient) {
        // Use the ID from the patient object (which is UUID) or let Firestore generate it?
        // Since we refactored Patient to have UUID default, we use it.
        // Or we can let Firestore document ID be the Patient.id
        patientsCollection.document(patient.id).set(patient).await()
    }

    override suspend fun updatePatient(patient: Patient) {
        patientsCollection.document(patient.id).set(patient).await()
    }
}
