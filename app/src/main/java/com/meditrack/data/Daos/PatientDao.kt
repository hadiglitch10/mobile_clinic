package com.meditrack.data.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meditrack.Patient

@Dao
interface PatientDao {
    @Insert
    suspend fun insertPatient(patient: Patient)

    @Query("SELECT * FROM patients")
    suspend fun getAllPatient(): List<Patient>

    @Update
    suspend fun updatePatient(patient: Patient)

    @Query("DELETE FROM patients WHERE id = :patientId")
    suspend fun deletePatient(patientId: Long)
}
