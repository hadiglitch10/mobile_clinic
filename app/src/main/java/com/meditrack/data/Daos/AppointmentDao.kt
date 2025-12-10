package com.meditrack.data.Daos
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.meditrack.data.entities.Appointment
import com.meditrack.data.entities.Patient

@Dao
interface AppointmentDao {
    @Insert
    suspend fun insertAppointment(appointment: Appointment)
    @Query("SELECT * from appointments")
    suspend fun getAllAppointment(patientId : Int) : List<Appointment>
    @Query("SELECT * from appointments")
    suspend fun getSingleAppointment(patientId : Int,appointmentId : Int) : Appointment
    @Update
    suspend fun updateAppointment(appointment:Appointment)
    @Delete
    suspend fun deleteAppointment(appointmentId:Int)
}