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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels.TaskDataViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// Función que calcula el tiempo restante (en horas) para una tarea

fun calculateRemainingHours(taskDate: String, taskStartTime: String?): Long {
    // Formateadores de fecha y hora
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateTimeFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    // Fecha y hora actuales
    val now = Calendar.getInstance().time

    // Combinar fecha y hora de la tarea
    val taskDateTime: Date = if (taskStartTime != null) {
        dateTimeFormatter.parse("$taskDate $taskStartTime") ?: now
    } else {
        dateFormatter.parse(taskDate) ?: now
    }

    // Calcular la diferencia en milisegundos y luego convertir a horas
    val differenceInMillis = taskDateTime.time - now.time
    return differenceInMillis / (1000 * 60 * 60)  // Convertir de milisegundos a horas
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PendingHoursView(
    navController: NavController,
    taskDataViewModel: TaskDataViewModel
) {
    // Observe tasks and loading state from the ViewModel
    val tasks by taskDataViewModel.tasks.collectAsState(initial = emptyList())
    val selectedTask by taskDataViewModel.selectedTask.collectAsState(initial = null)

    // Determine loading state
    val isLoading = tasks.isEmpty()

    Scaffold {
        if (isLoading) {
            // Show loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Sort tasks by remaining hours
            val sortedTasks = tasks.sortedBy { calculateRemainingHours(it.date, it.startTime) }

            // Display tasks in a list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(sortedTasks) { task ->
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
                        showSemaphore = false,
                        currentParticipants = task.currentParticipants,
                        maxParticipants = task.maxParticipants,
                        showStars = false,
                        rating = task.rating,
                        showRemainingInfo = true,
                        remainingHours = calculateRemainingHours(task.date, task.startTime),
                        onClick = {
                            taskDataViewModel.selectTask(task) // Update selected task
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

