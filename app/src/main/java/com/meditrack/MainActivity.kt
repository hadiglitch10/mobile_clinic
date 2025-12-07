package com.meditrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.meditrack.ui.theme.MediTrackTheme
import com.meditrack.ui.screens.*

enum class Screen {
    Home, PatientList, PatientDetail, Appointments, AddPatient, EditPatient, AddAppointment
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MediTrackTheme {
                // App-level state (in-memory)
                val patients = remember { mutableStateListOf<Patient>() }
                val appointments = remember { mutableStateListOf<Appointment>() }

                // seed sample data
                if (patients.isEmpty()) {
                    patients.add(Patient(id = 1L, name = "John Doe", age = 29, phone = "0100000000", email = "john@example.com", medicalHistory = "None"))
                    appointments.add(Appointment(id = 1L, patientId = 1L, dateTime = "2025-12-10T10:00", purpose = "General Checkup"))
                }

                var currentScreen by remember { mutableStateOf(Screen.Home) }
                var selectedPatientId by remember { mutableStateOf<Long?>(null) }
                var editingPatientId by remember { mutableStateOf<Long?>(null) }

                when (currentScreen) {
                    Screen.Home -> HomeScreen(
                        onAddPatient = { currentScreen = Screen.AddPatient },
                        onPatients = { currentScreen = Screen.PatientList },
                        onAppointments = { currentScreen = Screen.Appointments }
                    )
                    Screen.PatientList -> PatientListScreen(
                        patients = patients,
                        onBack = { currentScreen = Screen.Home },
                        onAddPatient = { currentScreen = Screen.AddPatient },
                        onPatientClick = { id ->
                            selectedPatientId = id
                            currentScreen = Screen.PatientDetail
                        }
                    )
                    Screen.PatientDetail -> {
                        val pid = selectedPatientId
                        PatientDetailScreen(
                            patient = patients.firstOrNull { it.id == pid },
                            onBack = { currentScreen = Screen.PatientList },
                            onEdit = {
                                editingPatientId = pid
                                currentScreen = Screen.EditPatient
                            }
                        )
                    }
                    Screen.Appointments -> AppointmentListScreen(
                        appointments = appointments,
                        patients = patients,
                        onBack = { currentScreen = Screen.Home },
                        onAddAppointment = { currentScreen = Screen.AddAppointment }
                    )
                    Screen.AddPatient -> AddEditPatientScreen(
                        patients = patients,
                        onSave = { newPatient ->
                            // assign simple incremental id
                            val nextId = (patients.maxOfOrNull { it.id } ?: 0L) + 1L
                            patients.add(newPatient.copy(id = nextId))
                            currentScreen = Screen.PatientList
                        },
                        onCancel = { currentScreen = Screen.PatientList },
                        editing = null
                    )
                    Screen.EditPatient -> AddEditPatientScreen(
                        patients = patients,
                        onSave = { updated ->
                            val idx = patients.indexOfFirst { it.id == updated.id }
                            if (idx >= 0) patients[idx] = updated
                            currentScreen = Screen.PatientDetail
                        },
                        onCancel = { currentScreen = Screen.PatientDetail },
                        editing = patients.firstOrNull { it.id == editingPatientId }
                    )
                    Screen.AddAppointment -> AddEditAppointmentScreen(
                        patients = patients,
                        appointments = appointments,
                        onSave = { ap ->
                            val nextId = (appointments.maxOfOrNull { it.id } ?: 0L) + 1L
                            appointments.add(ap.copy(id = nextId))
                            currentScreen = Screen.Appointments
                        },
                        onCancel = { currentScreen = Screen.Appointments }
                    )
                }
            }
        }
    }
}
