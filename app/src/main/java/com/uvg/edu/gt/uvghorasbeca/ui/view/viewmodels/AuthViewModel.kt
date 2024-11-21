package com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.edu.gt.uvghorasbeca.data.models.UserData
import com.uvg.edu.gt.uvghorasbeca.data.repository.FirebaseUserDataRepository
import com.uvg.edu.gt.uvghorasbeca.data.repository.UserDataRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // Instantiating FirebaseUserDataRepository directly
    private val authRepository: UserDataRepository = FirebaseUserDataRepository()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    // LiveData to hold user details
    private val _userDetails = MutableLiveData<UserData?>()
    val userDetails: LiveData<UserData?> = _userDetails

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        viewModelScope.launch {
            val isLoggedIn = authRepository.isLoggedIn()
            _authState.value = if (isLoggedIn) {
                loadUserDetails() // Load user details if authenticated
                AuthState.Authenticated
            } else {
                AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val result = authRepository.login(email, password)
                if (result.isSuccess) {
                    loadUserDetails()
                    _authState.postValue(AuthState.Authenticated)
                } else {
                    _authState.postValue(AuthState.Error(result.exceptionOrNull()?.message ?: "Login failed"))
                }
            } catch (e: Exception) {
                _authState.postValue(AuthState.Error("An unexpected error occurred: ${e.message}"))
            }
        }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authRepository.signup(email, password)
            if (result.isSuccess) {
                loadUserDetails()
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "Sign-up failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _userDetails.value = null // Clear user details
            _authState.value = AuthState.Unauthenticated
        }
    }

    private suspend fun loadUserDetails() {
        // Simulating data fetching, replace with actual repository calls to fetch user details
        val userDetails = UserData(
            id = "12345",
            email = "mockuser@uvg.edu.gt",
            password = "password123",
            completedHours = 40,
            pendingHours = 10,
            isAdmin = true
        )
        _userDetails.postValue(userDetails)
    }

    fun isAdmin(): Boolean {
        return _userDetails.value?.isAdmin ?: true
    }
}


sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
