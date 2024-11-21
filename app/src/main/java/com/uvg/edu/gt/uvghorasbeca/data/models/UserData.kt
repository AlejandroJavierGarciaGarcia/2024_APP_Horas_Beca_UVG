package com.uvg.edu.gt.uvghorasbeca.data.models

data class UserData(
    val id: String = "",
    val email: String = "",
    val completedHours: Int = 0,
    val pendingHours: Int = 0,
    val isAdmin: Boolean = false,
    val assignedActivities: List<String> = emptyList(),
    val publishedActivities: List<String> = emptyList()
) {
    // Explicit getter for Firestore compatibility
    fun getIsAdmin(): Boolean {
        return isAdmin
    }
}
