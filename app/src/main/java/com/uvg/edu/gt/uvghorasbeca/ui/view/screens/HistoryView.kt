package com.uvg.edu.gt.uvghorasbeca.ui.view.screens

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.uvg.edu.gt.uvghorasbeca.data.CustomCardData
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.CustomCard
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.PaginatedList


@Composable
fun HistoryView() {
    val items = List(100) { index ->
        CustomCardData(
            title = "Tutorías $index",
            location = "CIT - 126",
            date = "16 / 06 / 2024",
            timeRange = null,
            totalHours = "3.5",
            backgroundColor = Color.LightGray,
            showRating = true,
            rating = 5
        )
    }


    PaginatedList(items = items)
}


