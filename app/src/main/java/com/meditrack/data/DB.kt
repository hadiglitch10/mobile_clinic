package com.meditrack.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meditrack.Appointment
import com.meditrack.Patient
import com.meditrack.data.Daos.AppointmentDao
import com.meditrack.data.Daos.PatientDao

@Database(entities = [Patient::class, Appointment::class], version = 1)
abstract class DB : RoomDatabase() {
    abstract fun patientDao() : PatientDao
    abstract fun appointmentDao() : AppointmentDao
}