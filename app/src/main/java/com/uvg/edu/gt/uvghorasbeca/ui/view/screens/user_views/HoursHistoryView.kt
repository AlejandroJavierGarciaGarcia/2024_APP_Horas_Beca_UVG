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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import com.uvg.edu.gt.uvghorasbeca.data.repository.MockDataRepository
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HoursHistoryView(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    var tasks by remember { mutableStateOf(emptyList<Task>()) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    // Simulando una solicitud a backend
    LaunchedEffect(Unit) {
        delay(2000)  // Simula una espera de 2 segundos
        tasks = MockDataRepository.getAllTasks()  // Obtener los datos del repositorio
        isLoading = false
    }

    Scaffold(
    ) {
        if (isLoading) {
            // Mostrar indicador de carga mientras se obtienen los datos
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Mostrar la lista de tareas completadas con la opción de calificación
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
                        showSemaphore = false, // No mostrar el semáforo
                        currentParticipants = task.currentParticipants,
                        maxParticipants = task.maxParticipants,
                        showStars = true,  // Mostrar las estrellas
                        rating = task.rating,
                        showRemainingInfo = false, // No mostrar información de horas restantes
                        remainingHours = task.remainingHours,
                        onClick = {
//                            taskId ->
//                            selectedTask = tasks.find { it.id == taskId } // Lógica al pulsar
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
