package com.uvg.edu.gt.uvghorasbeca.data


data class AlertDialog(
    val title: String,
    val location: String,
    val date: String,
    val timeRange: String?,
    val totalHours: String?,
    val backgroundColor: androidx.compose.ui.graphics.Color,
    val showRating: Boolean,
    val rating: Int,
    val additionalInfo: String,
    val availableSpots: String
)