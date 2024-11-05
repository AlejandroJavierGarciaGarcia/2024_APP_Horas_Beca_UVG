package com.uvg.edu.gt.uvghorasbeca.data.models

data class UserData(
    val id: String,
    val email: String,
    val password: String,
    val completedHours: Int,
    val pendingHours: Int,
    val isAdmin: Boolean
)