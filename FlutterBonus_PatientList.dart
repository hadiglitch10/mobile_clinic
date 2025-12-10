import 'package:flutter/material.dart';

// 1. Data Model (Like your Kotlin data class)
class Patient {
  final int id;
  final String name;
  final int age;
  final String phone;

  Patient({required this.id, required this.name, required this.age, required this.phone});
}

// 2. Entry Point
void main() {
  runApp(const MediTrackApp());
}

// 3. App Configuration (Like your Theme setup)
class MediTrackApp extends StatelessWidget {
  const MediTrackApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'MediTrack Flutter Bonus',
      theme: ThemeData(
        primarySwatch: Colors.teal,
        useMaterial3: true,
      ),
      home: PatientListScreen(),
    );
  }
}

// 4. The Screen (Like your @Composable PatientListScreen)
class PatientListScreen extends StatelessWidget {
  // Mock data mimicking your ViewModel or Repository
  final List<Patient> patients = [
    Patient(id: 1, name: "John Doe", age: 29, phone: "0100000000"),
    Patient(id: 2, name: "Jane Smith", age: 34, phone: "0111111111"),
    Patient(id: 3, name: "Ahmed Ali", age: 45, phone: "0122222222"),
  ];

  PatientListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // Top Bar
      appBar: AppBar(
        title: const Text('MediTrack Patients'),
        backgroundColor: Colors.teal,
        foregroundColor: Colors.white,
      ),
      
      // List Content (Like LazyColumn)
      body: ListView.builder(
        padding: const EdgeInsets.all(8.0),
        itemCount: patients.length,
        itemBuilder: (context, index) {
          final patient = patients[index];
          return Card(
            elevation: 2,
            margin: const EdgeInsets.symmetric(vertical: 6.0),
            child: ListTile(
              // Leading Icon (Circle Avatar)
              leading: CircleAvatar(
                backgroundColor: Colors.teal.shade100,
                child: Text(patient.name[0], style: const TextStyle(fontWeight: FontWeight.bold)),
              ),
              
              // Title & Subtitle
              title: Text(patient.name, style: const TextStyle(fontWeight: FontWeight.bold)),
              subtitle: Text("Age: ${patient.age} | ${patient.phone}"),
              
              // Trailing Icon
              trailing: const Icon(Icons.arrow_forward_ios, size: 16),
              
              // Click Event
              onTap: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text("Selected: ${patient.name}"))
                );
              },
            ),
          );
        },
      ),
      
      // Floating Action Button (Like Scaffold's floatingActionButton)
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          // Action for adding patient
          print("Add Patient Clicked");
        },
        backgroundColor: Colors.teal,
        child: const Icon(Icons.add, color: Colors.white),
      ),
    );
  }
}
