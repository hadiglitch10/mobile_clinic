package com.meditrack.data

import androidx.room.TypeConverter
import com.meditrack.data.entities.Appointment
import com.meditrack.data.entities.Patient
import java.sql.Timestamp

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Timestamp? {
        return value?.let { Timestamp(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Timestamp?): Long? {
        return date?.time
    }
}
