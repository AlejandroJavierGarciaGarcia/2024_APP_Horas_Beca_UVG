package com.uvg.edu.gt.uvghorasbeca.data.repository

import com.uvg.edu.gt.uvghorasbeca.data.models.Task

object MockDataRepository {

    // Datos simulados para tareas
    private val tasks = listOf(
        Task(
            id = 1,
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
            id = 2,
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

    // Función para obtener todas las tareas
    fun getAllTasks(): List<Task> {
        return tasks
    }

    // Función para obtener una tarea por ID
    fun getTaskById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    // Puedes agregar más funciones aquí para otras listas de datos simulados,
    // como convocatorias abiertas, cerradas, en proceso, etc.
}
