package com.uvg.edu.gt.uvghorasbeca.data.repository

interface UserDataRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signup(email: String, password: String): Result<Unit> // Added signup
    fun logout()
    fun isLoggedIn(): Boolean
    fun isAdmin(): Boolean
    fun getUserHours(): Pair<Int, Int>
    fun getUsername(): String
}