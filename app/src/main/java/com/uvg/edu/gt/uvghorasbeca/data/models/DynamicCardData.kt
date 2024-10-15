package com.uvg.edu.gt.uvghorasbeca.data.models

import androidx.compose.ui.graphics.Color


data class Task(
    val title: String,
    val location: String,
    val date: String,
    val startTime: String?,
    val endTime: String?,
    val totalHoursCompleted: Float?,
    val isRecurring: Boolean = false,
    val recurrencePattern: String? = null,
    val currentParticipants: Int,
    val maxParticipants: Int,
//    val backgroundColor: Color,
    val rating: Int = 0,
    val remainingHours: Long? = null
)