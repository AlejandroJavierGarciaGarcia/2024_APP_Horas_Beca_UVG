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

    override suspend fun signup(email: String, password: String): Result<Unit> {
        return try {
            Log.d(TAG, "Attempting signup for email: $email")
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Log.d(TAG, "Signup successful. UID: ${user.uid}")
                writeUserToFirestore(user.uid, email)
                loadUserData(user.uid) // Populate cached user data
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Signup failed: ${e.message}", e)
            Result.failure(e)
        }
    }

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

    suspend fun getUserData(): UserData? {
        if (cachedUserData == null) {
            val uid = auth.currentUser?.uid ?: return null
            loadUserData(uid)
        }
        return cachedUserData
    }

    /**
     * Writes user data to Firestore.
     */
    private suspend fun writeUserToFirestore(uid: String, email: String) {
        try {
            Log.d(TAG, "Checking if user exists in Firestore. UID: $uid")
            val userDocument = usersCollection.document(uid).get().await()
            if (!userDocument.exists()) {
                Log.d(TAG, "User not found. Creating new document.")
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
                Log.d(TAG, "User document created successfully.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to write user data: ${e.message}", e)
        }
    }

    /**
     * Loads user data from Firestore and caches it.
     */
    private suspend fun loadUserData(uid: String) {
        try {
            Log.d(TAG, "Loading user data for UID: $uid")
            val userDocument = usersCollection.document(uid).get().await()
            if (userDocument.exists()) {
                cachedUserData = userDocument.toObject<UserData>()
                Log.d(TAG, "User data loaded: $cachedUserData")
            } else {
                Log.w(TAG, "User data not found for UID: $uid")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load user data: ${e.message}", e)
        }
    }
}
