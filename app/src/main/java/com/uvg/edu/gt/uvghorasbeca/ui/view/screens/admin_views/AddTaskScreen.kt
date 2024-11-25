package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.admin_views

import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.ui.theme.CustomColors
import com.uvg.edu.gt.uvghorasbeca.R
import java.util.Calendar
@Composable
fun AddTaskScreen(
    navController: NavController,
    onDismiss: () -> Unit,
    onSubmit: (Task) -> Unit,
    initialTask: Task? = null
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf(initialTask?.title ?: "") }
    var maxParticipants by remember { mutableStateOf(initialTask?.maxParticipants?.toString() ?: "") }
    var location by remember { mutableStateOf(initialTask?.location ?: "") }
    var date by remember { mutableStateOf(initialTask?.date ?: "") }
    var startTime by remember { mutableStateOf(initialTask?.startTime ?: "") }
    var endTime by remember { mutableStateOf(initialTask?.endTime ?: "") }
    var isRecurring by remember { mutableStateOf(initialTask?.isRecurring ?: false) }
    var recurrencePattern by remember { mutableStateOf(initialTask?.recurrencePattern ?: "") }
    var info by remember { mutableStateOf(initialTask?.info ?: "") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            date = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialogStart = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            startTime = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    val timePickerDialogEnd = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            endTime = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (initialTask != null) stringResource(id = R.string.edit_task_title) else stringResource(id = R.string.add_task_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo de Título
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(stringResource(id = R.string.task_name_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            // Campo de Cupo Máximo (solo números)
            OutlinedTextField(
                value = maxParticipants,
                onValueChange = { if (it.all { char -> char.isDigit() }) maxParticipants = it },
                label = { Text(stringResource(id = R.string.max_participants_label)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )

            // Campo de Fecha con selector de fecha
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text(stringResource(id = R.string.date_label)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .clickable { datePickerDialog.show() },
                readOnly = true
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            // Hora de Inicio con selector de hora
            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text(stringResource(id = R.string.start_time_label)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .clickable { timePickerDialogStart.show() },
                readOnly = true
            )

            // Hora de Fin con selector de hora
            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text(stringResource(id = R.string.end_time_label)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .clickable { timePickerDialogEnd.show() },
            )
        }

        // Campo de Lugar
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text(stringResource(id = R.string.location_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        // ¿Es recurrente?
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(stringResource(id = R.string.recurring_label), modifier = Modifier.weight(1f))
            Switch(
                checked = isRecurring,
                onCheckedChange = { isRecurring = it }
            )
        }

        // Campo de Recurrencia si es recurrente
        if (isRecurring) {
            OutlinedTextField(
                value = recurrencePattern,
                onValueChange = { recurrencePattern = it },
                label = { Text(stringResource(id = R.string.recurrence_pattern_label)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        // Campo de Información Adicional
        OutlinedTextField(
            value = info,
            onValueChange = { info = it },
            label = { Text(stringResource(id = R.string.additional_info_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de Cancelar y Agregar/Actualizar
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(CustomColors.RedButton),
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.cancel_button), color = CustomColors.White)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(CustomColors.PrimaryGreen),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (initialTask != null) stringResource(id = R.string.update_task_button) else stringResource(id = R.string.add_task_button), color = CustomColors.White)
            }
        }
    }
}

