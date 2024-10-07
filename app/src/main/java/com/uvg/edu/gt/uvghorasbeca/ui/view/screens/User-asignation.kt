package com.uvg.edu.gt.uvghorasbeca.ui.view.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.AlertDialogUser
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.PaginatedList


@Composable
fun UserView(navController : NavController) {
        AlertDialogUser(
            title = "Tutorías",
            location = "CIT-126",
            date = "16 / 06 / 2024",
            timeRange = "14:00 - 17:30",
            totalHours = null,
            backgroundColor = Color.LightGray,
            additionalInfo = "Esta actividad es organizada por el departamento de computación...",
            availableSpots = "0/10"
        )


}