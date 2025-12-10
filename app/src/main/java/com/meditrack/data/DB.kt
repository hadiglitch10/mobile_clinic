package com.meditrack.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.meditrack.Appointment
import com.meditrack.Patient
import com.meditrack.data.Daos.AppointmentDao
import com.meditrack.data.Daos.PatientDao

@Database(entities = [Patient::class, Appointment::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun appointmentDao(): AppointmentDao
}
