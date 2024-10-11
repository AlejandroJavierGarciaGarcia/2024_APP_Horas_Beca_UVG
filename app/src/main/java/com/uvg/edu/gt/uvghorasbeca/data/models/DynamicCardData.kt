package com.uvg.edu.gt.uvghorasbeca.data.models

import androidx.compose.ui.graphics.Color


data class Task(
    val title: String,
    val location: String,
    val date: String,
    val timeRange: String?,
    val totalHours: String?,
    val currentParticipants: Int,
    val maxParticipants: Int,
    val backgroundColor: Color
)