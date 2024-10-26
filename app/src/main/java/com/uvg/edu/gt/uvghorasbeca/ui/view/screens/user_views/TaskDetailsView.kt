package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.data.models.Task

@Composable
fun TaskDetailsView(navController: NavController, task: Task, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onDismiss),  // Cierra el modal al hacer clic fuera de la tarjeta
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(8.dp)
                .clickable(enabled = false) {},  // Desactiva clics dentro de la tarjeta
            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Título de la tarea y semáforo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.weight(1f)  // Limita el espacio que el título puede ocupar
                    ) {
                        Text(
                            text = task.title,
                            fontSize = 20.sp,
                            color = Color.Black,
                            maxLines = 1,  // Limita el texto a una sola línea
                            overflow = TextOverflow.Ellipsis  // Muestra "..." si el texto es demasiado largo
                        )
                    }
                    SemaphoreIndicator(task.currentParticipants, task.maxParticipants)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Línea divisoria
                Divider(color = Color.Gray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))

                // Lugar, recurrencia, fecha y hora
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = task.location,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )

                        if (task.isRecurring && task.recurrencePattern != null) {
                            Text(
                                text = "Recurrente: ${task.recurrencePattern}",
                                fontSize = 14.sp,
                                color = Color.Blue
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = task.date, fontSize = 14.sp, color = Color.DarkGray)
                        task.startTime?.let {
                            Text(text = "$it - ${task.endTime}", fontSize = 14.sp, color = Color.DarkGray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Información adicional
                task.info?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Información adicional:",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Justify
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)  // color de fondo
                    ) {
                        Text("Regresar", color = Color.White)  // color del texto
                    }

                    Button(
                        onClick = { /* lógica para asignarse a la tarea */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))  // color de fondo
                    ) {
                        Text("Asignarse", color = Color.White)  // color del texto
                    }
                }
            }
        }
    }
}

// Funciones de `DynamicCard` replicadas para usar en el modal:

@Composable
fun SemaphoreIndicator(currentParticipants: Int, maxParticipants: Int) {
    val fillRatio = currentParticipants.toFloat() / maxParticipants.toFloat()
    val colors = when {
        fillRatio <= 0.33f -> listOf(Color(0xFF00C853), Color(0xFFB0BEC5), Color(0xFFB0BEC5))
        fillRatio <= 0.66f -> listOf(Color(0xFFFFC107), Color(0xFFFFC107), Color(0xFFB0BEC5))
        else -> listOf(Color(0xFFFF5252), Color(0xFFFF5252), Color(0xFFFF5252))
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$currentParticipants/$maxParticipants",
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(6.dp))
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(width = 20.dp, height = 13.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

