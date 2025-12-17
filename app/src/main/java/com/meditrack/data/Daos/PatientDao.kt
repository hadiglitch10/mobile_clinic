package com.meditrack.data.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.meditrack.data.entities.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient)

    @Query("SELECT * FROM patients")
    fun getAllPatient(): Flow<List<Patient>>

    @Update
    suspend fun updatePatient(patient: Patient)

    @Query("DELETE FROM patients WHERE id = :patientId")
    suspend fun deletePatient(patientId: String)

    @Query("DELETE FROM patients")
    suspend fun clearAll()
}
