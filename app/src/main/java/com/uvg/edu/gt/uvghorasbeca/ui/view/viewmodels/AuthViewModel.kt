package com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uvg.edu.gt.uvghorasbeca.data.models.UserData
import com.uvg.edu.gt.uvghorasbeca.data.repository.FirebaseUserDataRepository
import com.uvg.edu.gt.uvghorasbeca.data.repository.UserDataRepository
import kotlinx.coroutines.launch


import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val authRepository: UserDataRepository = FirebaseUserDataRepository()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _userDetails = MutableLiveData<UserData?>()
    val userDetails: LiveData<UserData?> = _userDetails

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val isLoggedIn = authRepository.isLoggedIn()
            _authState.value = if (isLoggedIn) {
                loadUserDetails()
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
                // Create user entry in Firestore after signup
                createUserEntryInFirestore()
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
        val currentUser = auth.currentUser
        if (currentUser != null) {
            try {
                val userDocument = firestore.collection("Users").document(currentUser.uid).get().await()
                if (userDocument.exists()) {
                    val userDetails = userDocument.toObject(UserData::class.java)
                    _userDetails.postValue(userDetails)
                } else {
                    // Handle case where user document does not exist
                    _userDetails.postValue(null)
                    _authState.postValue(AuthState.Error("User data not found."))
                }
            } catch (e: Exception) {
                _authState.postValue(AuthState.Error("Failed to load user details: ${e.message}"))
            }
        } else {
            _authState.postValue(AuthState.Unauthenticated)
        }
    }

    private suspend fun createUserEntryInFirestore() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val newUser = UserData(
                id = currentUser.uid,
                email = currentUser.email ?: "",
                completedHours = 0,
                pendingHours = 0,
                isAdmin = false,
                assignedActivities = emptyList(),
                publishedActivities = emptyList()
            )
            try {
                firestore.collection("Users").document(currentUser.uid).set(newUser).await()
            } catch (e: Exception) {
                _authState.postValue(AuthState.Error("Failed to create user entry in Firestore: ${e.message}"))
            }
        }
    }

    fun isAdmin(): Boolean {
        return _userDetails.value?.isAdmin ?: false
    }
}



sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
