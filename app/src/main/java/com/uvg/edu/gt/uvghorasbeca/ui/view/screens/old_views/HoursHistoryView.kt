package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.old_views

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.data.models.CustomCardData
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.PaginatedList


@Composable
fun HistoryView(navController : NavController) {
    val items = List(100) { index ->
        CustomCardData(
            title = "Tutorías $index",
            location = "CIT - 126",
            date = "16 / 06 / 2024",
            timeRange = null,
            totalHours = "3.5",
            backgroundColor = Color(0xFFe0f1ff),
            showRating = true,
            rating = 5
        )
    }


    PaginatedList(items = items)
}


