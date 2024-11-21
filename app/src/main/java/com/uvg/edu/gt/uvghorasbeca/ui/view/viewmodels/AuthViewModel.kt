package com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel

import android.util.Log
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
    private val tasks = emptyList<String>()

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
            loadUserDetails()
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
                val userDocument = firestore.collection("Users").document(currentUser.uid)
                    .get(com.google.firebase.firestore.Source.SERVER).await()

                if (userDocument.exists()) {
                    // Log raw data
                    Log.d("AuthViewModel", "Raw Firestore data: ${userDocument.data}")

                    val userDetails = userDocument.toObject(UserData::class.java)
                    if (userDetails != null) {
                        _userDetails.postValue(userDetails)

                        // Log user admin status
                        Log.d("AuthViewModel", "Deserialized user data: $userDetails")
                        if (userDetails.isAdmin) {
                            Log.d("AuthViewModel", "User is an admin.")
                        } else {
                            Log.d("AuthViewModel", "User is not an admin.")
                        }
                    } else {
                        Log.d("AuthViewModel", "User data could not be deserialized.")
                    }
                } else {
                    Log.w("AuthViewModel", "User document not found.")
                    _userDetails.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Failed to load user details: ${e.message}", e)
            }
        } else {
            Log.d("AuthViewModel", "No user is currently logged in.")
        }
    }

    fun getAssignedActivities(): List<String> {
        return _userDetails.value?.assignedActivities ?: emptyList()
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
