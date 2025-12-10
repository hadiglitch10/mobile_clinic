package com.meditrack

import org.junit.Assert.*
import org.junit.Test

class AppointmentValidatorTest {

    @Test
    fun isPurposeValid_emptyPurpose_returnsFalse() {
        val result = AppointmentValidator.validatePurpose("")
        assertFalse(result)
    }

    @Test
    fun isPurposeValid_nonEmptyPurpose_returnsTrue() {
        val result = AppointmentValidator.validatePurpose("General Checkup")
        assertTrue(result)
    }

    @Test
    fun isDateValid_emptyDate_returnsFalse() {
        val result = AppointmentValidator.validateDate("")
        assertFalse(result)
    }

    @Test
    fun isDateValid_validDate_returnsTrue() {
        val result = AppointmentValidator.validateDate("2025-12-01T10:00")
        assertTrue(result)
    }
}

object AppointmentValidator {
    fun validatePurpose(purpose: String): Boolean {
        return purpose.isNotEmpty()
    }

    fun validateDate(date: String): Boolean {
        return date.isNotEmpty()
    }
}
