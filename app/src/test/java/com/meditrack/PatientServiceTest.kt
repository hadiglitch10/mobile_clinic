package com.meditrack

import com.meditrack.data.Daos.PatientDao
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.test.runTest

class PatientServiceTest {

    @Mock
    lateinit var patientDao: PatientDao

    @InjectMocks
    lateinit var patientService: PatientService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        patientService = PatientService(patientDao)
    }

    @Test
    fun addPatient_verifiesDaoCall() = runTest {
        val patient = Patient(id = 1, name = "John Doe", age = 30, phone = "123", email = "test@test.com", medicalHistory = "None")
        
        patientService.addPatient(patient)
        
        verify(patientDao).insertPatient(patient)
    }

    @Test
    fun getAllPatients_returnsList() = runTest {
        val expectedList = listOf(
            Patient(id = 1, name = "John", age = 30, phone = "123", email = "a@a.com", medicalHistory = "None"),
            Patient(id = 2, name = "Jane", age = 25, phone = "456", email = "b@b.com", medicalHistory = "Asthma")
        )
        
        `when`(patientDao.getAllPatient()).thenReturn(expectedList)
        
        val result = patientService.getAllPatients()
        
        assertEquals(expectedList.size, result.size)
        assertEquals(expectedList[0].name, result[0].name)
    }
}

// Simple Service class for testing context
class PatientService(private val patientDao: PatientDao) {
    suspend fun addPatient(patient: Patient) {
        patientDao.insertPatient(patient)
    }

    suspend fun getAllPatients(): List<Patient> {
        return patientDao.getAllPatient()
    }
}
