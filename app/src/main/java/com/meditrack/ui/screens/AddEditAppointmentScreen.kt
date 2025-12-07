package com.meditrack.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meditrack.Appointment
import com.meditrack.Patient

@Composable
fun AddEditAppointmentScreen(
    patients: List<Patient>,
    appointments: List<Appointment>,
    onSave: (Appointment) -> Unit,
    onCancel: () -> Unit
) {
    var patientIdStr by remember { mutableStateOf("") }
    var dateTimeStr by remember { mutableStateOf("2025-12-01T10:00") }
    var purpose by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text("Add Appointment")

        OutlinedTextField(
            value = patientIdStr,
            onValueChange = { patientIdStr = it.filter { ch -> ch.isDigit() } },
            label = { Text("Patient ID") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        OutlinedTextField(
            value = dateTimeStr,
            onValueChange = { dateTimeStr = it },
            label = { Text("DateTime (e.g. 2025-12-01T10:00)") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        OutlinedTextField(
            value = purpose,
            onValueChange = { purpose = it },
            label = { Text("Purpose") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        if (error.isNotBlank()) Text(error, color = androidx.compose.ui.graphics.Color.Red)

        Button(onClick = {
            val pid = patientIdStr.toLongOrNull()
            if (pid == null) {
                error = "Patient ID required"
                return@Button
            }
            if (purpose.isBlank()) {
                error = "Purpose required"
                return@Button
            }
            // check patient exists
            val exists = patients.any { it.id == pid }
            if (!exists) {
                error = "Patient not found"
                return@Button
            }

            val ap = Appointment(id = 0L, patientId = pid, dateTime = dateTimeStr.trim(), purpose = purpose.trim())
            onSave(ap)
        }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
            Text("Save")
        }

        Button(onClick = onCancel, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Cancel")
        }
    }
}
