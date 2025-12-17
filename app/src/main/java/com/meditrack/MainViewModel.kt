package com.meditrack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.meditrack.data.AppDatabase
import com.meditrack.data.AuthRepository
import com.meditrack.data.AppointmentRepository
import com.meditrack.data.PatientRepository
import com.meditrack.data.entities.Appointment
import com.meditrack.data.entities.Patient
import com.meditrack.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.room.Room
import com.google.firebase.auth.FirebaseUser

class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    // Database initialization (Manual DI for simplicity)
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "meditrack-db"
    )
    .fallbackToDestructiveMigration()
    .build()

    private val firestore = FirebaseFirestore.getInstance()

    private val authRepository = AuthRepository()
    private val patientRepository = PatientRepository(db.patientDao(), firestore)
    private val appointmentRepository = AppointmentRepository(db.appointmentDao(), firestore)

    // Exposed Flows
    val patients: StateFlow<List<Patient>> = patientRepository.getAllPatients()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val appointments: StateFlow<List<Appointment>> = appointmentRepository.getAllAppointments()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // User Preferences
    private val userPreferencesRepository = UserPreferencesRepository(application)

    val clinicName: StateFlow<String> = userPreferencesRepository.clinicName
        .stateIn(viewModelScope, SharingStarted.Lazily, "My Clinic")

    val notificationsEnabled: StateFlow<Boolean> = userPreferencesRepository.notificationsEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val isDarkTheme: StateFlow<Boolean> = userPreferencesRepository.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun updateClinicName(name: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveClinicName(name)
        }
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveNotificationsEnabled(enabled)
        }
    }

    fun updateTheme(isDark: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveThemePreference(isDark)
        }
    }

    // Auth
    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            if (result.isSuccess) {
                onResult(true, null)
            } else {
                onResult(false, result.exceptionOrNull()?.message)
            }
        }
    }

    fun register(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.register(email, password)
            if (result.isSuccess) {
                onResult(true, null)
            } else {
                onResult(false, result.exceptionOrNull()?.message)
            }
        }
    }
    
    fun logout() {
        authRepository.logout()
    }

    // Data Operations
    fun addPatient(patient: Patient) {
        viewModelScope.launch {
            patientRepository.addPatient(patient)
        }
    }

    fun updatePatient(patient: Patient) {
        viewModelScope.launch {
            patientRepository.updatePatient(patient)
        }
    }
    
    fun deletePatient(patientId: String) {
        viewModelScope.launch {
            patientRepository.deletePatient(patientId)
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentRepository.addAppointment(appointment)
        }
    }
    
    fun deleteAppointment(appointmentId: String) {
        viewModelScope.launch {
            appointmentRepository.deleteAppointment(appointmentId)
        }
    }
}
