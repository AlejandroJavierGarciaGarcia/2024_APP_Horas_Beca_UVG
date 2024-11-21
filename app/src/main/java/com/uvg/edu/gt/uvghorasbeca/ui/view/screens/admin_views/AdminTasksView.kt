package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.admin_views

import CustomCard
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.ui.theme.CustomColors
import com.uvg.edu.gt.uvghorasbeca.navigation.NavigationState
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels.TaskDataViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminTasksView(
    navController: NavController,
    taskDataViewModel: TaskDataViewModel
) {
    val tasks by taskDataViewModel.allTasks.collectAsState(initial = emptyList())
    val selectedTask by taskDataViewModel.selectedTask.collectAsState(initial = null)

    // Refresh de tasks cuando carga el view
    LaunchedEffect(Unit) {
        taskDataViewModel.fetchAllTasks()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavigationState.AddTask.route) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Tarea")
            }
        }
    ) {
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
                        showSemaphore = true,
                        currentParticipants = task.currentParticipants,
                        maxParticipants = task.maxParticipants,
                        showStars = false,
                        rating = task.rating,
                        showRemainingInfo = false,
                        remainingHours = task.remainingHours,
                        onClick = { taskDataViewModel.selectTask(task) } // Update selected task in ViewModel
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Show task details if a task is selected
            if (selectedTask != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CustomColors.GrayOpacity60)
                ) {
                    selectedTask?.let {
                        AdminTaskDetailsView(
                            navController = navController,
                            task = it,
                            onDismiss = { taskDataViewModel.selectTask(null) } // Clear selected task
                        )
                    }
                }
            }
        }
    }



