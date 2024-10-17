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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AvailableTasksView(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    var tasks by remember { mutableStateOf(emptyList<Task>()) }

    // Simulando una solicitud a backend
    LaunchedEffect(Unit) {
        delay(2000)  // Simula una espera de 2 segundos
        tasks = listOf(  // Datos simulados
            Task(
                title = "Staff de Delvas",
                location = "CIT - 336",
                date = "27/06/2024",
                startTime = "13:00",
                endTime = "14:00",
                totalHoursCompleted = null,
                isRecurring = false,
                recurrencePattern = null,
                currentParticipants = 4,
                maxParticipants = 7,
                rating = 3,
                remainingHours = 12
            ),
            Task(
                title = "Auxiliatura",
                location = "Departamento de Computación",
                date = "27/06/2024",
                startTime = "13:00",
                endTime = "14:00",
                totalHoursCompleted = null,
                isRecurring = true,
                recurrencePattern = "Semanal",
                currentParticipants = 0,
                maxParticipants = 1,
                rating = 2,
                remainingHours = 43
            )
        )
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
            // Mostrar la lista de tareas una vez que los datos están cargados
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(tasks) { task ->
                    CustomCard(
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
                        remainingHours = task.remainingHours
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


