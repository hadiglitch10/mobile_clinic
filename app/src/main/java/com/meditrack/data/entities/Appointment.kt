package com.meditrack.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "appointments",
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["id"],
        childColumns = ["patientId"],
    )],
    )
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val patientId : Int,
    val date : Timestamp,
    val status : String,
    val purpose: String
)