package com.meditrack.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = false)
    @DocumentId
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: String = "",
    val phone: String? = null,
    val email: String? = null,
    val medicalHistory: String? = null,
    val diagnosis: String = "",
    val contactInfo: String = ""
)
