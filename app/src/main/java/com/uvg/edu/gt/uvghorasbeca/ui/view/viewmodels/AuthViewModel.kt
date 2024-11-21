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

/**
 * ViewModel encargado de autenticacion e informacion del usuario
 */
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

    // Verificacion de estado de autenticacion
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

    // Funcion de login
    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Correo o contraseña vacíos")
            return
        }

        // Auth -> loading mientras se procesa el login
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val result = authRepository.login(email, password)
                if (result.isSuccess) {
                    loadUserDetails()
                    _authState.postValue(AuthState.Authenticated)
                } else {
                    _authState.postValue(AuthState.Error(result.exceptionOrNull()?.message ?: "Error en el login"))
                }
            } catch (e: Exception) {
                _authState.postValue(AuthState.Error("Error: ${e.message}"))
            }
            loadUserDetails()
        }
    }

    // Funcion de registro
    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Correo o contraseña vacíos")
            return
        }

        // Auth -> loading mientras se procesa el signup
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authRepository.signup(email, password)
            if (result.isSuccess) {
                // Crea instancia de usuario en Firestore
                createUserEntryInFirestore()
                loadUserDetails()
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "Error en el registro")
            }
        }
    }

    // Funcion de logout
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _userDetails.value = null
            _authState.value = AuthState.Unauthenticated
        }
    }

    // Funcion para cargar detalles de un usuario a FireStore
    private suspend fun loadUserDetails() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            try {
                // Si existe un documento correspondiente al UID en FireStore
                val userDocument = firestore.collection("Users").document(currentUser.uid)
                    .get(com.google.firebase.firestore.Source.SERVER).await()

                if (userDocument.exists()) {
                    // Log raw data
                    Log.d("AuthViewModel", "Raw Firestore data: ${userDocument.data}")

                    // Se serializan los datos
                    val userDetails = userDocument.toObject(UserData::class.java)
                    if (userDetails != null) {
                        _userDetails.postValue(userDetails)
                    } else {
                        Log.d("AuthViewModel", "Error serializando datos del usuario")
                    }
                } else {
                    Log.w("AuthViewModel", "Informacion de Usuario no Existente")
                    _userDetails.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error cargando detalles de usuario: ${e.message}", e)
            }
        } else {
            Log.d("AuthViewModel", "Se intentaron actualizar los datos sin login")
        }
    }

    // Retorna actividades asignadas de un usuario
    fun getAssignedActivities(): List<String> {
        return _userDetails.value?.assignedActivities ?: emptyList()
    }

    // Crea el entry del usuario en FireStore
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
                _authState.postValue(AuthState.Error("No se pudo crear un nuevo usuario en Firestore: ${e.message}"))
            }
        }
    }

    // Admin -> True, No Admin -> False
    fun isAdmin(): Boolean {
        return _userDetails.value?.isAdmin ?: false
    }
}

// Estados de autenticacion
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
