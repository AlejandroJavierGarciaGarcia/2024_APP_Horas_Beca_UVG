package com.uvg.edu.gt.uvghorasbeca.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.uvg.edu.gt.uvghorasbeca.data.models.UserData

class MockUserRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    private var currentUser: UserData? = null

    // Cargar el estado de sesión desde SharedPreferences
    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean("isLoggedIn", false)
        private set(value) = sharedPreferences.edit().putBoolean("isLoggedIn", value).apply()

    var isAdmin: Boolean
        get() = sharedPreferences.getBoolean("isAdmin", false)
        private set(value) = sharedPreferences.edit().putBoolean("isAdmin", value).apply()

    // Método para iniciar sesión
    fun login(email: String, password: String): Boolean {
        val user = MockUserData.userList.find {
            it.email == email && it.password == password && it.email.endsWith("@uvg.edu.gt")
        }

        return if (user != null) {
            currentUser = user
            isLoggedIn = true
            isAdmin = user.isAdmin

            // Guardar el ID del usuario actual en SharedPreferences
            sharedPreferences.edit().putString("userId", user.id).apply()
            true
        } else {
            false
        }
    }

    // Método para cerrar sesión
    fun logout() {
        currentUser = null
        isLoggedIn = false
        isAdmin = false
        sharedPreferences.edit().remove("userId").apply()
    }

    // Método para cargar los datos del usuario actual al iniciar la app
    fun loadUserData() {
        if (currentUser == null) { // Solo carga si no se ha cargado ya
            val userId = sharedPreferences.getString("userId", null)
            currentUser = MockUserData.userList.find { it.id == userId }
        }
    }

    // Obtener horas completadas y pendientes
    fun getUserHours(): Pair<Int, Int> {
        loadUserData()  // se asefura de que currentUser esté cargado
        return currentUser?.let { it.completedHours to it.pendingHours } ?: 0 to 0
    }

    // Obtener el nombre de usuario basado en el correo electrónico
    fun getUsername(): String {
        loadUserData()  // se asegura de que currentUser esté cargado
        return currentUser?.email?.substringBefore("@") ?: ""
    }

}
