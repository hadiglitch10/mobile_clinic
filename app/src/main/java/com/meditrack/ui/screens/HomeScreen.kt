package com.meditrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List

@Composable
fun HomeScreen(onAddPatient: () -> Unit, onPatients: () -> Unit, onAppointments: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            CenterAlignedTopAppBar(title = { Text("MediTrack") })

            Spacer(modifier = Modifier.height(36.dp))

            Button(onClick = onAddPatient, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Patient")
            }

            Button(onClick = onPatients, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Icon(Icons.Default.List, contentDescription = "Patients")
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Patients")
            }

            Button(onClick = onAppointments, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Text("Appointments")
            }
        }
    }
}
