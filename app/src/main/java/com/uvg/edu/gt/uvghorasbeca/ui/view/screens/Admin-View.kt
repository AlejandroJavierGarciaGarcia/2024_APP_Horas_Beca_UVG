package com.uvg.edu.gt.uvghorasbeca.ui.view.screens
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.uvg.edu.gt.uvghorasbeca.ui.theme.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.R
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.CustomCardAdmin
import java.text.SimpleDateFormat
import java.util.*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminController(modifier: Modifier = Modifier, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(bottom = 16.dp),
                    containerColor = NotImportantColor
                ) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.adding))
                }
            }
        },
        content = { // Aquí envolvemos la Column en LazyColumn
            LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                items(listOf( // Usamos items para definir las tarjetas
                    CustomCardData("Tutorías Cálculo 2", "CIT - 503", "16 / 10 / 2024", "4"),
                    CustomCardData("Staff de la Cueva UVG", "Cueva - UVG", "20 / 10 / 2024", "5"),
                    CustomCardData("Ensayo de Música", "Salón de Música - CIT", "21 / 10 / 2024", "3"),
                    CustomCardData("Cuidado de DHIVE", "DHIVE - UVG", "22 / 10 / 2024", "2"),
                    CustomCardData("Atención a Vida Estudiantil", "Oficina de Vida Estudiantil", "23 / 10 / 2024", "4"),
                    CustomCardData("Organización de Evento Deportivo", "Campo Deportivo UVG", "25 / 10 / 2024", "6"),
                    CustomCardData("Taller de Creatividad", "Aula de Arte - CIT", "27 / 10 / 2024", "3.5")
                )) { card ->
                    CustomCardAdmin(
                        title = card.title,
                        location = card.location,
                        date = card.date,
                        timeRange = null,
                        totalHours = card.totalHours,
                        backgroundColor = Color(0xFFe0f1ff),
                        showRating = false
                    )
                }
            }
        }
    )
    // Modal Dialog
    if (showDialog) {
        ActivityModal(onDismiss = { showDialog = false })
    }
}

data class CustomCardData(val title: String, val location: String, val date: String, val totalHours: String)


@Composable
fun ActivityModal(onDismiss: () -> Unit) {
    var activityName by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }
    var participantCount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(Calendar.getInstance().time) }
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(id = R.string.dialog_title)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = activityName,
                    onValueChange = { activityName = it },
                    label = { Text(stringResource(id = R.string.name_activity)) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(id = R.string.enable_activity))
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
                    label = { Text(stringResource(id = R.string.participants)) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(id = R.string.description)) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = room,
                    onValueChange = { room = it },
                    label = { Text(stringResource(id = R.string.clasroom)) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = convertMillisToDate(date.time), // Convertimos date a String
                    onValueChange = { /* No se permite editar, solo visualización */ },
                    label = { Text(stringResource(id = R.string.date)) },
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                imageVector = Icons.Sharp.DateRange,
                                contentDescription = stringResource(id = R.string.date_asignation)
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
                Text(text = " * ${stringResource(id = R.string.creation)}: ${convertMillisToDate(date.time)}")
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
                        contentDescription = stringResource(id = R.string.adding),
                        modifier = Modifier.size(14.dp),
                        tint = Color.White  // Color del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    Text(
                        stringResource(id = R.string.adding),
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
                        contentDescription = stringResource(id = R.string.cancel),
                        modifier = Modifier.size(14.dp),
                        tint = Color.White  // Color del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    Text(
                        stringResource(id = R.string.cancel),
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
                        contentDescription = stringResource(id = R.string.adding),
                        modifier = Modifier.size(14.dp),
                        tint = Color.White  // Color del ícono
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
                    Text(
                        stringResource(id = R.string.adding),
                        color = Color.White
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
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.cancel),
                        modifier = Modifier.size(14.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(id = R.string.cancel),
                        color = Color.White
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
