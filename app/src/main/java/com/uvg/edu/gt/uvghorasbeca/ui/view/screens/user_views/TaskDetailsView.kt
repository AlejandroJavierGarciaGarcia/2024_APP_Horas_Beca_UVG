package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp


@Composable
fun TaskDetailsView(navController: NavController, task: Task, onDismiss: () -> Unit) {
    // Usamos un Card para hacer que los detalles se vean resaltados
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título de la tarea
            Text(text = task.title, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar la ubicación
            Text(text = "Ubicación: ${task.location}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar la fecha y la hora de inicio y fin
            Text(text = "Fecha: ${task.date}")
            Text(text = "Hora: ${task.startTime ?: "N/A"} - ${task.endTime ?: "N/A"}")

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar los participantes
            Text(text = "Participantes: ${task.currentParticipants}/${task.maxParticipants}")

            Spacer(modifier = Modifier.height(16.dp))

            // Si es recurrente, mostramos esa información
            if (task.isRecurring) {
                Text(text = "Recurrente: ${task.recurrencePattern ?: "Patrón no especificado"}")
            }

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón para regresar
                Button(onClick = onDismiss) {
                    Text("Regresar")
                }

                // Botón para asignarse a la tarea
                Button(onClick = { /* lógica para asignarse a la tarea */ }) {
                    Text("Asignarse")
                }
            }
        }
    }
}

