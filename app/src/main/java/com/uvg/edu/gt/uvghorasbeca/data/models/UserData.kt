package com.uvg.edu.gt.uvghorasbeca.data.models

// Datos del usuario
data class UserData(
    val id: String = "",
    val email: String = "",
    val completedHours: Int = 0,
    val pendingHours: Int = 0,
    val isAdmin: Boolean = false,
    val assignedActivities: List<String> = emptyList(),
    val publishedActivities: List<String> = emptyList()
) {
    fun getIsAdmin(): Boolean {
        return isAdmin
    }
}
