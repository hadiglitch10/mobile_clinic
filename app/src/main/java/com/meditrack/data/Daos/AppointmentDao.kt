package com.meditrack.data.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meditrack.Appointment

@Dao
interface AppointmentDao {
    @Insert
    suspend fun insertAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointments WHERE patientId = :patientId")
    suspend fun getAppointmentsForPatient(patientId: Long): List<Appointment>

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Query("DELETE FROM appointments WHERE id = :appointmentId")
    suspend fun deleteAppointment(appointmentId: Long)
}
