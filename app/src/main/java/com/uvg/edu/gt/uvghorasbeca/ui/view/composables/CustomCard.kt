package com.uvg.edu.gt.uvghorasbeca.ui.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import com.uvg.edu.gt.uvghorasbeca.ui.theme.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CustomCard(
    title: String,
    location: String,
    date: String,
    timeRange: String?,
    totalHours: String?,
    backgroundColor: Color,
    showRating: Boolean = false,
    rating: Int = 0
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (showRating) {
                        Row {
                            repeat(rating) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Star",
                                    tint = Color.Yellow,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info Icon",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }


                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = location,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = date,
                            fontSize = 14.sp
                        )
                        timeRange?.let {
                            Text(
                                text = it,
                                fontSize = 14.sp
                            )
                        }

                        totalHours?.let {
                            Text(
                                text = "$it h",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }



        }
    }
}

@Composable
fun CustomCardAdmin(
    title: String,
    location: String,
    date: String,
    timeRange: String?,
    totalHours: String?,
    backgroundColor: Color,
    showRating: Boolean = false,
    rating: Int = 0
) {
    var showOptionsDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (showRating) {
                        Row {
                            repeat(rating) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Star",
                                    tint = Color.Yellow,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    IconButton(onClick = { showOptionsDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info Icon",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp) // Icono más grande
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = location,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = date,
                            fontSize = 14.sp
                        )
                        timeRange?.let {
                            Text(
                                text = it,
                                fontSize = 14.sp
                            )
                        }

                        totalHours?.let {
                            Text(
                                text = "$it h",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal con opciones de Editar y Eliminar
    if (showOptionsDialog) {
        AlertDialog(
            onDismissRequest = { showOptionsDialog = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Opciones", fontWeight = FontWeight.Bold)  // Título "Opciones"
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio adicional

                    // IconButton para cerrar en la parte superior izquierda
                    IconButton(
                        onClick = { showOptionsDialog = false },
                        modifier = Modifier
                            .size(24.dp)  // Tamaño del botón
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,  // Ícono de "X" para cerrar
                            contentDescription = "Cerrar",
                            tint = Color.Black
                        )
                    }


                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(color = Color.Gray, thickness = 0.4.dp)

                    // Fila con los botones Editar y Eliminar
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        // Botón Editar con ícono y texto en negro
                        Button(
                            onClick = { showEditDialog = true; showOptionsDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // Naranja
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Edit,  // Ícono de edición
                                    contentDescription = "Editar",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Black  // Color del ícono
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                                Text(
                                    "Editar",
                                    color = Color.Black  // Texto en negro
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))  // Espacio entre los dos botones

                        // Botón Eliminar con ícono y texto
                        Button(
                            onClick = { showDeleteConfirmDialog = true; showOptionsDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Rojo
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Delete,  // Ícono de eliminar
                                    contentDescription = "Eliminar",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White  // Color del ícono
                                )
                                Spacer(modifier = Modifier.width(8.dp))  // Espacio entre el ícono y el texto
                                Text(
                                    "Eliminar",
                                    color = Color.White  // Texto en blanco
                                )
                            }
                        }
                    }

                    Divider(color = Color.Gray, thickness = 0.3.dp)
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    // Confirmación de eliminación
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta actividad?") },
            confirmButton = {
                Button(
                    onClick = { showDeleteConfirmDialog = false; showOptionsDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Check,  // Ícono de edición
                            contentDescription = "Confirmar",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White  // Color del ícono
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                        Text(
                            "Confirmar",
                            color = Color.White  // Texto en negro
                        )
                    }
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteConfirmDialog = false; showOptionsDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27C24C)) // Naranja
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Close,  // Ícono de edición
                            contentDescription = "Cancelar",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White  // Color del ícono
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                        Text(
                            "Cancelar",
                            color = Color.White  // Texto en negro
                        )
                    }
                }
            }
        )
    }

    // Modal de edición
    if (showEditDialog) {
        EditActivityModal(
            activityNameInitial = "Nombre Existente",
            isActiveInitial = true,
            participantCountInitial = "15",
            descriptionInitial = "Descripción de la actividad existente",
            roomInitial = "Aula 202",
            dateInitial = Calendar.getInstance().time,
            onDismiss = { showEditDialog = false }
        )    }
}

@Composable
fun EditActivityModal(
    activityNameInitial: String,
    isActiveInitial: Boolean,
    participantCountInitial: String,
    descriptionInitial: String,
    roomInitial: String,
    dateInitial: Date,
    onDismiss: () -> Unit
) {
    var activityName by remember { mutableStateOf(activityNameInitial) }
    var isActive by remember { mutableStateOf(isActiveInitial) }
    var participantCount by remember { mutableStateOf(participantCountInitial) }
    var description by remember { mutableStateOf(descriptionInitial) }
    var room by remember { mutableStateOf(roomInitial) }
    var date by remember { mutableStateOf(dateInitial) }
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Editar Actividad") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = activityName,
                    onValueChange = { activityName = it },
                    label = { Text("Nombre de la Actividad") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("¿Actividad Activa?")
                    Switch(
                        checked = isActive,
                        onCheckedChange = { isActive = it },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = Green,
                            uncheckedTrackColor = GrayOpacity,
                            checkedThumbColor = NotImportantColor,
                            uncheckedThumbColor = Black
                        )
                    )
                }
                OutlinedTextField(
                    value = participantCount,
                    onValueChange = { participantCount = it },
                    label = { Text("Cantidad de Participantes") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = room,
                    onValueChange = { room = it },
                    label = { Text("Salón") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = convertMillisToDate(date.time),
                    onValueChange = { /* Solo visualización */ },
                    label = { Text("Seleccione la fecha") },
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                imageVector = Icons.Sharp.DateRange,
                                contentDescription = "Fecha de asignación"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(64.dp)
                )
                if (showDatePicker) {
                    DatePickerModalInput(
                        onDateSelected = { selectedDateMillis ->
                            date = Date(selectedDateMillis ?: date.time)
                            showDatePicker = false
                        },
                        onDismiss = { showDatePicker = false }
                    )
                }
                Text(text = " * Creación: ${convertMillisToDate(date.time)}")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Manejar la acción de confirmación aquí
                    onDismiss() // Cerrar el modal después de agregar
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27C24C)) // Verde
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Check,  // Ícono de confirmar
                        contentDescription = "Actualizar",
                        modifier = Modifier.size(14.dp),
                        tint = Color.White  // Color del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    Text(
                        "Agregar",
                        color = Color.White  // Texto en blanco
                    )
                }
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Rojo
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,  // Ícono de cancelar
                        contentDescription = "Cancelar",
                        modifier = Modifier.size(14.dp),
                        tint = Color.White  // Color del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    Text(
                        "Cancelar",
                        color = Color.White  // Texto en blanco
                    )
                }
            }
        }

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    // Manejar la acción de confirmación aquí
                    onDismiss() // Cerrar el modal después de agregar
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27C24C)) // Verde
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Check,  // Ícono de confirmar
                        contentDescription = "Confimar",
                        modifier = Modifier.size(14.dp),
                        tint = Color.White  // Color del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    Text(
                        "Confirmar",
                        color = Color.White  // Texto en blanco
                    )
                }
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Rojo
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,  // Ícono de cancelar
                        contentDescription = "Cancelar",
                        modifier = Modifier.size(14.dp),
                        tint = Color.White  // Color del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    Text(
                        "Cancelar",
                        color = Color.White  // Texto en blanco
                    )
                }
            }
        }
,
        text = {
            DatePicker(state = datePickerState)
        }
    )
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
