package com.meditrack

import com.meditrack.data.Daos.AppointmentDao
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.test.runTest

class AppointmentServiceTest {

    @Mock
    lateinit var appointmentDao: AppointmentDao

    @InjectMocks
    lateinit var appointmentService: AppointmentService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        appointmentService = AppointmentService(appointmentDao)
    }

    @Test
    fun addAppointment_verifiesDaoCall() = runTest {
        val appointment = Appointment(id = 1, patientId = 100, dateTime = "2025-12-01T10:00", purpose = "Checkup")
        
        appointmentService.addAppointment(appointment)
        
        verify(appointmentDao).insertAppointment(appointment)
    }

    @Test
    fun getAppointmentsForPatient_returnsList() = runTest {
        val patientId = 100L
        val expectedList = listOf(
            Appointment(id = 1, patientId = patientId, dateTime = "2025-01-01", purpose = "Follow up"),
            Appointment(id = 2, patientId = patientId, dateTime = "2025-02-01", purpose = "Cleaning")
        )
        
        `when`(appointmentDao.getAppointmentsForPatient(patientId)).thenReturn(expectedList)
        
        val result = appointmentService.getAppointmentsForPatient(patientId)
        
        assertEquals(expectedList.size, result.size)
        assertEquals(expectedList[0].purpose, result[0].purpose)
    }
}

// Simple Service class for testing context
class AppointmentService(private val appointmentDao: AppointmentDao) {
    suspend fun addAppointment(appointment: Appointment) {
        appointmentDao.insertAppointment(appointment)
    }

    suspend fun getAppointmentsForPatient(patientId: Long): List<Appointment> {
        return appointmentDao.getAppointmentsForPatient(patientId)
    }
}
