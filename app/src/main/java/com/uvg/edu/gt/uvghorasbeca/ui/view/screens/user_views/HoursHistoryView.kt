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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels.TaskDataViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HoursHistoryView(
    navController: NavController,
    taskDataViewModel: TaskDataViewModel
) {
    val tasks by taskDataViewModel.allTasks.collectAsState(initial = emptyList())
    val selectedTask by taskDataViewModel.selectedTask.collectAsState(initial = null)

    // Refresh de tasks cuando carga el view
    LaunchedEffect(Unit) {
        taskDataViewModel.fetchAllTasks()
    }
    Scaffold {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(tasks) { task ->
                    CustomCard(
                        id = task.id,
                        title = task.title,
                        location = task.location,
                        date = task.date,
                        startTime = task.startTime,
                        endTime = task.endTime,
                        totalHoursCompleted = task.totalHoursCompleted,
                        isRecurring = task.isRecurring,
                        recurrencePattern = task.recurrencePattern,
                        showSemaphore = false,
                        currentParticipants = task.currentParticipants,
                        maxParticipants = task.maxParticipants,
                        showStars = true,
                        rating = task.rating,
                        showRemainingInfo = false,
                        remainingHours = task.remainingHours,
                        onClick = { taskDataViewModel.selectTask(task) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (selectedTask != null) {
                // Placeholder for task details or any additional action
                // E.g., TaskDetailsView(navController, selectedTask)
            }
        }
    }