package com.meditrack.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val name : String,
    val age : Int,
    val phone : String,
    val email : String,
    val history : String,
)