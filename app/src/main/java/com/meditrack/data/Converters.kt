package com.meditrack.data

import androidx.room.TypeConverter
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
