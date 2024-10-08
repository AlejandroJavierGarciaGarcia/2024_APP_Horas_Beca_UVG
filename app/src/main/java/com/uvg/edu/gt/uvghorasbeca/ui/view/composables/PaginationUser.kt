package com.uvg.edu.gt.uvghorasbeca.ui.view.composables

import AlertDialogUser
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.uvg.edu.gt.uvghorasbeca.R
import com.uvg.edu.gt.uvghorasbeca.data.AlertDialog

@Composable
fun PaginatedAlertDialogList(
    items: List<AlertDialog>,
    itemsPerPage: Int = 4
) {
    var currentPage by remember { mutableStateOf(0) }
    val totalPages = (items.size + itemsPerPage - 1) / itemsPerPage

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // List of AlertDialogs displayed for the current page
        LazyColumn(modifier = Modifier.weight(1f)) {
            val startIndex = currentPage * itemsPerPage
            val endIndex = minOf(startIndex + itemsPerPage, items.size)

            items(items.subList(startIndex, endIndex)) { item ->
                AlertDialogUser(
                    title = item.title,
                    location = item.location,
                    date = item.date,
                    timeRange = item.timeRange,
                    totalHours = item.totalHours,
                    backgroundColor = item.backgroundColor,
                    showRating = item.showRating,
                    rating = item.rating,
                    additionalInfo = item.additionalInfo,
                    availableSpots = item.availableSpots
                )
            }
        }

        // Pagination controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { if (currentPage > 0) currentPage-- },
                enabled = currentPage > 0
            ) {
                Text(text = stringResource(id = R.string.previous_button))
            }

            Button(
                onClick = { if (currentPage < totalPages - 1) currentPage++ },
                enabled = currentPage < totalPages - 1
            ) {
                Text(text = stringResource(id = R.string.next_button))
            }
        }
    }
}