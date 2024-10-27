package com.uvg.edu.gt.uvghorasbeca.data.models

data class Task(
    val id: Int,
    val title: String,
    val location: String,
    val date: String,
    val startTime: String?,
    val endTime: String?,
    val totalHoursCompleted: Float? = null,
    val isRecurring: Boolean = false,
    val recurrencePattern: String? = null,
    val currentParticipants: Int = 0,
    val maxParticipants: Int,
//    val backgroundColor: Color,
    val rating: Int = 0,
    val remainingHours: Long? = null,
    val info: String? = null
)

