package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views

import CustomCard
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app.ui.theme.CustomColors
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel.AuthViewModel
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels.TaskDataViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AvailableTasksView(
    navController: NavController,
    taskViewModel: TaskDataViewModel = viewModel(),
) {
    val tasks by taskViewModel.availableTasks.collectAsState(initial = emptyList())
    val authViewModel: AuthViewModel = AuthViewModel()
    val selectedTask by taskViewModel.selectedTask.collectAsState()

    // Refresh de tasks cuando carga el view
    LaunchedEffect(Unit) {
        taskViewModel.fetchAvailableTasks()
    }

    Scaffold {
        // Esto hay que quitarlo, se queda en un loop si uno no tiene tasks asignados
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                        onClick = { taskViewModel.selectTask(task) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            if (selectedTask != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CustomColors.GrayOpacity60)
                ) {
                    TaskDetailsView(
                        navController = navController,
                        task = selectedTask!!,
                        onDismiss = { taskViewModel.selectTask(null) },
                        authViewModel = authViewModel,
                        taskDataViewModel = taskViewModel
                    )
                }
            }
        }
    }
}