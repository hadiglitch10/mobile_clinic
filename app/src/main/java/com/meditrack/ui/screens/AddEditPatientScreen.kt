package com.meditrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meditrack.Patient

@Composable
fun AddEditPatientScreen(
    patients: List<Patient>,
    onSave: (Patient) -> Unit,
    onCancel: () -> Unit,
    editing: Patient?
) {
    var name by remember { mutableStateOf(editing?.name ?: "") }
    var ageStr by remember { mutableStateOf(editing?.age?.toString() ?: "") }
    var phone by remember { mutableStateOf(editing?.phone ?: "") }
    var email by remember { mutableStateOf(editing?.email ?: "") }
    var history by remember { mutableStateOf(editing?.medicalHistory ?: "") }

    var nameError by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Text(if (editing == null) "Add Patient" else "Edit Patient")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        if (nameError) Text("Name is required", color = androidx.compose.ui.graphics.Color.Red)

        OutlinedTextField(
            value = ageStr,
            onValueChange = { ageStr = it.filter { ch -> ch.isDigit() } },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        OutlinedTextField(
            value = history,
            onValueChange = { history = it },
            label = { Text("Medical History") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                nameError = name.isBlank()
                if (!nameError) {
                    val id = editing?.id ?: 0L
                    val patient = Patient(
                        id = id,
                        name = name.trim(),
                        age = ageStr.toIntOrNull(),
                        phone = phone.trim().ifBlank { null },
                        email = email.trim().ifBlank { null },
                        medicalHistory = history.trim().ifBlank { null }
                    )
                    onSave(patient)
                }
            }) {
                Text("Save")
            }

            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}
