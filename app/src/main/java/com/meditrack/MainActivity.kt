package com.meditrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
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
                val viewModel: MainViewModel = viewModel()
                val patients = viewModel.patients
                val appointments = viewModel.appointments

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
                            viewModel.addPatient(newPatient)
                            currentScreen = Screen.PatientList
                        },
                        onCancel = { currentScreen = Screen.PatientList },
                        editing = null
                    )
                    Screen.EditPatient -> AddEditPatientScreen(
                        patients = patients,
                        onSave = { updated ->
                            viewModel.updatePatient(updated)
                            currentScreen = Screen.PatientDetail
                        },
                        onCancel = { currentScreen = Screen.PatientDetail },
                        editing = patients.firstOrNull { it.id == editingPatientId }
                    )
                    Screen.AddAppointment -> AddEditAppointmentScreen(
                        patients = patients,
                        onSave = { ap ->
                            viewModel.addAppointment(ap)
                            currentScreen = Screen.Appointments
                        },
                        onCancel = { currentScreen = Screen.Appointments }
                    )
                }
            }
        }
    }
}
