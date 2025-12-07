package com.meditrack.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meditrack.Patient

@Composable
fun PatientListScreen(
    patients: List<Patient>,
    onBack: () -> Unit,
    onAddPatient: () -> Unit,
    onPatientClick: (Long) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filtered = if (query.isBlank()) patients else patients.filter {
        it.name.lowercase().contains(query.lowercase()) || (it.phone ?: "").contains(query)
    }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        TopAppBar(title = { Text("Patients") }, navigationIcon = {
            IconButton(onClick = onBack) { Text("Back") }
        })

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search by name or phone") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filtered) { p ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onPatientClick(p.id) }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(p.name, style = MaterialTheme.typography.titleMedium)
                        Text(p.phone ?: "-", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        Button(onClick = onAddPatient, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Add Patient")
        }
    }
}
