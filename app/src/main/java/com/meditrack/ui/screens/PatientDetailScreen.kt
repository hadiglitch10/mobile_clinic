package com.meditrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meditrack.Patient

@Composable
fun PatientDetailScreen(patient: Patient?, onEdit: () -> Unit, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        TopAppBar(title = { Text("Patient Detail") })

        if (patient == null) {
            Text("Patient not found", modifier = Modifier.padding(top = 16.dp))
        } else {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Name: ${patient.name}", modifier = Modifier.padding(top = 8.dp))
            Text("Age: ${patient.age ?: "-"}", modifier = Modifier.padding(top = 4.dp))
            Text("Phone: ${patient.phone ?: "-"}", modifier = Modifier.padding(top = 4.dp))
            Text("Email: ${patient.email ?: "-"}", modifier = Modifier.padding(top = 4.dp))
            Text("History: ${patient.medicalHistory ?: "-"}", modifier = Modifier.padding(top = 4.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = onEdit) { Text("Edit") }
                Button(onClick = onBack) { Text("Back") }
            }
        }
    }
}
