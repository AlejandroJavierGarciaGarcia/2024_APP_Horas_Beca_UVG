package com.uvg.edu.gt.uvghorasbeca.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.uvg.edu.gt.uvghorasbeca.data.models.UserData
import kotlinx.coroutines.tasks.await

class FirebaseUserDataRepository : UserDataRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("Users")
    private var cachedUserData: UserData? = null

    companion object {
        private const val TAG = "FirebaseUserDataRepo"
    }

    // Funcion de login
    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            Log.d(TAG, "Attempting login for email: $email")
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Log.d(TAG, "Login successful. UID: ${user.uid}")
                writeUserToFirestore(user.uid, email)
                loadUserData(user.uid) // Populate cached user data
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Login failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    // Funcion de signup
    override suspend fun signup(email: String, password: String): Result<Unit> {
        return try {
            Log.d(TAG, "Attempting signup for email: $email")
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Log.d(TAG, "Signup successful. UID: ${user.uid}")
                // Crea datos del usuario en firestore y los carga en el app
                writeUserToFirestore(user.uid, email)
                loadUserData(user.uid)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Signup failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    // Funcion de logout
    override fun logout() {
        Log.d(TAG, "Logging out user.")
        cachedUserData = null
        auth.signOut()
    }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun isAdmin(): Boolean {
        cachedUserData?.let {
            return it.isAdmin
        }
        return try {
            val uid = auth.currentUser?.uid ?: return false
            val userDocument = usersCollection.document(uid).get().await()
            userDocument.getBoolean("isAdmin") ?: false
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch isAdmin: ${e.message}", e)
            false
        }
    }

    override suspend fun getUserHours(): Pair<Int, Int> {
        // Primero busca si hay valores cached, si no va a la DB
        cachedUserData?.let {
            return it.completedHours to it.pendingHours
        }
        return try {
            val uid = auth.currentUser?.uid ?: return 0 to 0
            val userDocument = usersCollection.document(uid).get().await()
            val completedHours = userDocument.getLong("completedHours")?.toInt() ?: 0
            val pendingHours = userDocument.getLong("pendingHours")?.toInt() ?: 0
            completedHours to pendingHours
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch user hours: ${e.message}", e)
            0 to 0
        }
    }

    override fun getUsername(): String {
        return cachedUserData?.email ?: auth.currentUser?.email?.substringBefore("@") ?: ""
    }

    // Crea un nuevo usuario en Firestore
    private suspend fun writeUserToFirestore(uid: String, email: String) {
        try {
            val userDocument = usersCollection.document(uid).get().await()
            if (!userDocument.exists()) {
                val userData = mapOf(
                    "id" to uid,
                    "email" to email,
                    "completedHours" to 0,
                    "pendingHours" to 0,
                    "isAdmin" to false,
                    "assignedActivities" to emptyList<String>(),
                    "publishedActivities" to emptyList<String>()
                )
                usersCollection.document(uid).set(userData).await()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creando usuario: ${e.message}", e)
        }
    }


    private suspend fun loadUserData(uid: String) {
        try {
            val userDocument = usersCollection.document(uid).get().await()
            if (userDocument.exists()) {
                cachedUserData = userDocument.toObject<UserData>()
            } else {
                Log.w(TAG, "No hay registro para ID: $uid")
            }
        } catch (e: Exception) {
            Log.e(TAG, "No se pudo cargar la informacion del usuario: ${e.message}", e)
        }
    }

    override suspend fun AssignmentTask(taskId: String) {
        try {
            // Obtiene el UID del usuario logueado
            val uid = auth.currentUser?.uid ?: return

            // Obtiene el documento del usuario desde Firestore
            val userDocument = usersCollection.document(uid).get().await()

            if (userDocument.exists()) {
                // Obtiene las actividades asignadas como una lista mutable
                val assignedActivities = (userDocument.get("assignedActivities") as? MutableList<String>) ?: mutableListOf()

                // Verifica si la tarea ya está asignada
                if (!assignedActivities.contains(taskId)) {
                    assignedActivities.add(taskId) // Agrega la tarea al array
                    usersCollection.document(uid).update("assignedActivities", assignedActivities).await()
                    Log.d(TAG, "Tarea $taskId asignada exitosamente al usuario $uid.")
                } else {
                    Log.d(TAG, "La tarea $taskId ya estaba asignada al usuario $uid.")
                }
            } else {
                Log.e(TAG, "El documento del usuario $uid no existe.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error asignando tarea: ${e.message}", e)
        }
    }

}
