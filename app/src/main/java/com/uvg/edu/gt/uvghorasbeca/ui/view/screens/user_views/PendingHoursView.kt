package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views

import CustomCard
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

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
fun PendingHoursView(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    var tasks by remember { mutableStateOf(emptyList<Task>()) }

    // Simulando una solicitud a backend
    LaunchedEffect(Unit) {
        delay(2000)  // Simula una espera de 2 segundos
        tasks = listOf(  // Datos simulados
            Task(
                title = "Staff de Delvas",
                location = "CIT - 336",
                date = "16/10/2024",
                startTime = "05:00",
                endTime = "14:00",
                totalHoursCompleted = null,
                isRecurring = false,
                recurrencePattern = null,
                currentParticipants = 6,
                maxParticipants = 7,
                rating = 3
            ),
            Task(
                title = "Auxiliatura",
                location = "Departamento de Computación",
                date = "16/10/2024",
                startTime = "13:00",
                endTime = "14:00",
                totalHoursCompleted = null,
                isRecurring = true,
                recurrencePattern = "Semanal",
                currentParticipants = 0,
                maxParticipants = 1,
                rating = 2
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
            // Ordenar las tareas por horas restantes
            val sortedTasks = tasks.sortedBy { calculateRemainingHours(it.date, it.startTime) }

            // Mostrar la lista de tareas una vez que los datos están cargados
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(sortedTasks) { task ->
                    CustomCard(
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
                        remainingHours = calculateRemainingHours(task.date, task.startTime)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
