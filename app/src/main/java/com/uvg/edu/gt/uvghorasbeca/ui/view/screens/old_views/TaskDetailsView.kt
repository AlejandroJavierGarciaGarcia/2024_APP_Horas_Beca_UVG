package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.old_views
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.R
import AlertDialog
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.PaginatedAlertDialogList

@Composable
fun UserView(navController: NavController) {
    // Generate a list of AlertDialog items
    val items = List(100) { index ->
        AlertDialog(
            title = stringResource(id = R.string.activity_title, index + 1),
            location = stringResource(id = R.string.classroom_location), // Random classroom number
            date = stringResource(id = R.string.activity_date),
            timeRange = stringResource(id = R.string.activity_time_range), // Random time range
            totalHours = null,
            backgroundColor = Color(0xFFe0f1ff),
            showRating = false,
            rating = 0,
            additionalInfo = stringResource(id = R.string.activity_info, index + 1),
            availableSpots = stringResource(id = R.string.available_spots)
        )
    }

    PaginatedAlertDialogList(items = items)
}
