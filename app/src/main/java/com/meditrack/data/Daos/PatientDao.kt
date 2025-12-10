package com.meditrack.data.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meditrack.data.entities.Appointment
import com.meditrack.data.entities.Patient

@Dao
interface PatientDao {
    @Insert
    suspend fun insertPatient(patient: Patient)
    @Query("SELECT * from patients")
    suspend fun getAllPatient() : List<Patient>
    @Query("SELECT * from appointments")
    suspend fun getSingleAppointment(patientId : Int,appointmentId : Int) : Appointment
    @Update
    suspend fun updatePatient(patient:Patient)
    @Delete
    suspend fun deletePatient(patientId:Int)
}