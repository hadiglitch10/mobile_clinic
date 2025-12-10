package com.meditrack

import org.junit.Assert.*
import org.junit.Test

class PatientValidatorTest {

    @Test
    fun isPatientNameValid_emptyName_returnsFalse() {
        val result = PatientValidator.validateName("")
        assertFalse(result)
    }

    @Test
    fun isPatientNameValid_nonEmptyName_returnsTrue() {
        val result = PatientValidator.validateName("John Doe")
        assertTrue(result)
    }

    @Test
    fun isPatientAgeValid_zeroAge_returnsFalse() {
        val result = PatientValidator.validateAge(0)
        assertFalse(result)
    }

    @Test
    fun isPatientAgeValid_negativeAge_returnsFalse() {
        val result = PatientValidator.validateAge(-5)
        assertFalse(result)
    }

    @Test
    fun isPatientAgeValid_positiveAge_returnsTrue() {
        val result = PatientValidator.validateAge(25)
        assertTrue(result)
    }
}

object PatientValidator {
    fun validateName(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun validateAge(age: Int): Boolean {
        return age > 0
    }
}
