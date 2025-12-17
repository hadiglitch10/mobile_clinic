package com.meditrack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.meditrack.data.AuthRepository

import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository: AuthRepository = AuthRepository()

    var currentUser by mutableStateOf<FirebaseUser?>(null)
        private set

    init {
        currentUser = repository.currentUser
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = repository.login(email, pass)
            if (result.isSuccess) {
                currentUser = result.getOrNull()
                onSuccess()
            } else {
                onError(result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun register(email: String, pass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val result = repository.register(email, pass)
            if (result.isSuccess) {
                currentUser = result.getOrNull()
                onSuccess()
            } else {
                onError(result.exceptionOrNull()?.message ?: "Registration failed")
            }
        }
    }

    fun signOut() {
        repository.logout()
        currentUser = null
    }
}
