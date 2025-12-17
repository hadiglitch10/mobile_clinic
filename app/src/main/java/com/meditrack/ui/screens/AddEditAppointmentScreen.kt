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
import androidx.compose.material3.*

import com.meditrack.data.entities.Appointment
import com.meditrack.data.entities.Patient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAppointmentScreen(
    patients: List<Patient>,
    onSave: (Appointment) -> Unit,
    onCancel: () -> Unit
) {
    var patientIdStr by remember { mutableStateOf("") }
    var dateTimeStr by remember { mutableStateOf("2025-12-01T10:00") }
    var purpose by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text("Add Appointment")

        // Dropdown for Patient Selection
        var expanded by remember { mutableStateOf(false) }
        var selectedPatientName by remember { mutableStateOf("") }

        // Initialize selected name if editing or ID exists
        LaunchedEffect(patientIdStr) {
            val p = patients.find { it.id == patientIdStr }
            if (p != null) selectedPatientName = p.name
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            OutlinedTextField(
                value = selectedPatientName.ifEmpty { "Select Patient" },
                onValueChange = {},
                readOnly = true,
                label = { Text("Patient") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                patients.forEach { patient ->
                    DropdownMenuItem(
                        text = { Text(patient.name) },
                        onClick = {
                            patientIdStr = patient.id
                            selectedPatientName = patient.name
                            expanded = false
                        }
                    )
                }
            }
        }

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
            // In a real app, this should be a dropdown. For now, we take entered string or pick first if empty?
            // Actually, user must enter an ID. Or we can show a list of patients to pick from.
            
            val pid = patientIdStr.trim()
            if (pid.isBlank()) {
                error = "Patient ID required"
                return@Button
            }
            if (purpose.isBlank()) {
                error = "Purpose required"
                return@Button
            }
            // check patient exists by ID
            val exists = patients.any { it.id == pid }
            if (!exists) {
                error = "Patient with this ID not found"
                return@Button
            }

            val ap = Appointment(id = "", patientId = pid, dateTime = dateTimeStr.trim(), purpose = purpose.trim())
            onSave(ap)
        }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
            Text("Save")
        }

        Button(onClick = onCancel, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Cancel")
        }
    }
}
