package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views

import CustomCard
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels.TaskDataViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HoursHistoryView(
    navController: NavController,
    taskDataViewModel: TaskDataViewModel
) {
    // Observe tasks and selectedTask state from the ViewModel
    val tasks by taskDataViewModel.tasks.collectAsState(initial = emptyList())
    val selectedTask by taskDataViewModel.selectedTask.collectAsState(initial = null)

    // Determine loading state
    val isLoading = tasks.isEmpty()

    Scaffold {
        if (isLoading) {
            // Show loading indicator while fetching data
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Display the list of completed tasks with the option for rating
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(tasks) { task ->
                    CustomCard(
                        id = task.id.toInt(),
                        title = task.title,
                        location = task.location,
                        date = task.date,
                        startTime = task.startTime,
                        endTime = task.endTime,
                        totalHoursCompleted = task.totalHoursCompleted,
                        isRecurring = task.isRecurring,
                        recurrencePattern = task.recurrencePattern,
                        showSemaphore = false, // Do not show the semaphore
                        currentParticipants = task.currentParticipants,
                        maxParticipants = task.maxParticipants,
                        showStars = true,  // Show stars for rating
                        rating = task.rating,
                        showRemainingInfo = false, // Do not show remaining hours info
                        remainingHours = task.remainingHours,
                        onClick = { taskDataViewModel.selectTask(task) } // Update selected task in ViewModel
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Handle task selection if needed
            if (selectedTask != null) {
                // Placeholder for task details or any additional action
                // E.g., TaskDetailsView(navController, selectedTask)
            }
        }
    }
}

