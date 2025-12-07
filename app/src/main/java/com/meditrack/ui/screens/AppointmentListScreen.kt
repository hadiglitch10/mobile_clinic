package com.meditrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meditrack.Appointment
import com.meditrack.Patient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentListScreen(
    appointments: List<Appointment>,
    patients: List<Patient>,
    onBack: () -> Unit,
    onAddAppointment: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        TopAppBar(title = { Text("Appointments") })

        Text("Today's & Upcoming", modifier = Modifier.padding(top = 8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(appointments) { a ->
                val patientName = patients.firstOrNull { it.id == a.patientId }?.name ?: "Unknown"
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Text("$patientName - ${a.purpose}")
                    Text("At: ${a.dateTime}", modifier = Modifier.padding(top = 4.dp))
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("Back") }
            Button(onClick = onAddAppointment) { Text("Add Appointment") }
        }
    }
}
