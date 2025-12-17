package com.meditrack.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.meditrack.data.Daos.PatientDao
import com.meditrack.data.entities.Patient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PatientRepository(
    private val patientDao: PatientDao,
    private val firestore: FirebaseFirestore
) {
    private val patientsRef = firestore.collection("patients")
    private var snapshotListener: ListenerRegistration? = null

    init {
        startSync()
    }

    private fun startSync() {
        snapshotListener = patientsRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("PatientRepo", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val patients = snapshot.toObjects(Patient::class.java)
                    // Simple sync strategy: Clear and Replace. 
                    // For more complex apps, use differential updates.
                    // But here, to ensure deletes are propagated, replace is safest for 'sync all'.
                    // OR: Iterate and upsert. But deletes are tricky without soft deletes or full replace.
                    // Given the request for "streaming", full replace on snapshot is acceptable for small datasets.
                    // However, to avoid flickering, let's use Upsert logic + Delete missing.
                    
                    // Actually, easiest way to align with Firestore state for small data is to just Insert/Update all.
                    // How to handle deletes? If it's not in snapshot, it should be gone from Room.
                    // So, clearAll + InsertAll is the brute force.
                    // Optimization: Get current Room data, diff it.
                    // For now, let's stick to strict sync:
                    // 1. Get all IDs from snapshot.
                    // 2. Upsert all snapshot items to Room.
                    // 3. Delete items from Room that are NOT in snapshot (optional, if we want strict mirroring).
                    
                    // Let's do a simple clear and repopulate for correctness first. 
                    // Optimization can be done if performance issues arise.
                    patientDao.clearAll()
                    patients.forEach { patientDao.insertPatient(it) }
                }
            }
        }
    }

    fun getAllPatients(): Flow<List<Patient>> = patientDao.getAllPatient()

    suspend fun addPatient(patient: Patient) {
        val doc = if (patient.id.isBlank()) patientsRef.document() else patientsRef.document(patient.id)
        val patientWithId = patient.copy(id = doc.id)
        doc.set(patientWithId).await()
        // No need to insert to Room manually, listener will pick it up
    }

    suspend fun updatePatient(patient: Patient) {
        patientsRef.document(patient.id).set(patient).await()
    }

    suspend fun deletePatient(patientId: String) {
        patientsRef.document(patientId).delete().await()
    }

    fun stopSync() {
        snapshotListener?.remove()
    }
}
