package com.meditrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meditrack.ui.theme.MediTrackTheme
import com.meditrack.ui.screens.*
import com.meditrack.data.entities.Appointment
import com.meditrack.data.entities.Patient

enum class Screen {
    Login, Register, Home, PatientList, PatientDetail, Appointments, AddPatient, EditPatient, AddAppointment, Settings
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = viewModel()
            // Collect flows
            val patients by viewModel.patients.collectAsState()
            val appointments by viewModel.appointments.collectAsState()
            
            val clinicName by viewModel.clinicName.collectAsState()
            val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            
            MediTrackTheme(darkTheme = isDarkTheme) {
                
                // Determine initial screen based on auth state
                var currentScreen by remember { mutableStateOf(if (viewModel.currentUser != null) Screen.Home else Screen.Login) }
                
                var selectedPatientId by remember { mutableStateOf<String?>(null) }
                var editingPatientId by remember { mutableStateOf<String?>(null) }

                when (currentScreen) {
                    Screen.Login -> LoginScreen(
                        onLoginSuccess = { currentScreen = Screen.Home },
                        onNavigateToRegister = { currentScreen = Screen.Register },
                        onLogin = { email, pass, cb -> viewModel.login(email, pass, cb) }
                    )
                    Screen.Register -> RegisterScreen(
                        onRegisterSuccess = { currentScreen = Screen.Home },
                        onNavigateToLogin = { currentScreen = Screen.Login },
                        onRegister = { email, pass, cb -> viewModel.register(email, pass, cb) }
                    )
                    Screen.Home -> HomeScreen(
                        clinicName = clinicName,
                        onAddPatient = { currentScreen = Screen.AddPatient },
                        onPatients = { currentScreen = Screen.PatientList },
                        onAppointments = { currentScreen = Screen.Appointments },
                        onSettings = { currentScreen = Screen.Settings }
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
                    Screen.Settings -> SettingsScreen(
                        currentClinicName = clinicName,
                        currentNotificationsEnabled = notificationsEnabled,
                        currentIsDarkTheme = isDarkTheme,
                        onClinicNameChange = { viewModel.updateClinicName(it) },
                        onNotificationsChange = { viewModel.updateNotificationsEnabled(it) },
                        onThemeChange = { viewModel.updateTheme(it) },
                        onBack = { currentScreen = Screen.Home }
                    )
                }
            }
        }
    }
}
