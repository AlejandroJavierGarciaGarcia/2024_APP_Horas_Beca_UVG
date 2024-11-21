package com.uvg.edu.gt.uvghorasbeca.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseUserDataRepository : UserDataRepository {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signup(email: String, password: String): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun isAdmin(): Boolean {
        // Example: Replace with custom claims or Firestore role-check logic
        return false
    }

    override fun getUserHours(): Pair<Int, Int> {
        // Placeholder for fetching user hours (e.g., from Firestore)
        return 0 to 0
    }

    override fun getUsername(): String {
        return auth.currentUser?.email?.substringBefore("@") ?: ""
    }
}
