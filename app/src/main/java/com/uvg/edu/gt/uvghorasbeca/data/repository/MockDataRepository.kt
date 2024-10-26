package com.uvg.edu.gt.uvghorasbeca.data.repository

import com.uvg.edu.gt.uvghorasbeca.data.models.Task

object MockDataRepository {

    // Datos simulados para tareas
    private val tasks = listOf(
        Task(
            id = 1,
            title = "Staff de Delvas",
            location = "CIT - 336",
            date = "28/10/2024",
            startTime = "14:00",
            endTime = "16:00",
            totalHoursCompleted = null,
            isRecurring = false,
            recurrencePattern = null,
            currentParticipants = 4,
            maxParticipants = 7,
            rating = 3,
            remainingHours = 12,
            info = "Esta es una actividad de apoyo al staff de Delvas en el CIT. La tarea consiste en asistir a los coordinadores en la organización de los eventos del área."
        ),
        Task(
            id = 2,
            title = "Auxiliatura",
            location = "Departamento de Computación",
            date = "29/10/2024",
            startTime = "09:00",
            endTime = "11:00",
            totalHoursCompleted = null,
            isRecurring = true,
            recurrencePattern = "Semanal",
            currentParticipants = 0,
            maxParticipants = 1,
            rating = 2,
            remainingHours = 43,
            info = "Apoyo en la Auxiliatura del Departamento de Computación, brindando soporte técnico y académico a los estudiantes."
        ),
        Task(
            id = 3,
            title = "Mantenimiento de Laboratorio",
            location = "Laboratorio de Física",
            date = "30/10/2024",
            startTime = "08:00",
            endTime = "12:00",
            totalHoursCompleted = null,
            isRecurring = true,
            recurrencePattern = "Mensual",
            currentParticipants = 1,
            maxParticipants = 5,
            rating = 4,
            remainingHours = 20,
            info = "Colaboración en el mantenimiento preventivo de los equipos del laboratorio, incluyendo la limpieza y verificación de su correcto funcionamiento."
        ),
        Task(
            id = 4,
            title = "Capacitación",
            location = "Aula Magna",
            date = "01/11/2024",
            startTime = "10:00",
            endTime = "13:00",
            totalHoursCompleted = null,
            isRecurring = false,
            recurrencePattern = null,
            currentParticipants = 10,
            maxParticipants = 20,
            rating = 5,
            remainingHours = 3,
            info = "Capacitación en primeros auxilios para el personal de la universidad, con enfoque en la respuesta rápida ante situaciones de emergencia."
        ),
        Task(
            id = 5,
            title = "Taller de Office",
            location = "Sala de Computación",
            date = "02/11/2024",
            startTime = "09:30",
            endTime = "12:30",
            totalHoursCompleted = null,
            isRecurring = true,
            recurrencePattern = "Semanal",
            currentParticipants = 13,
            maxParticipants = 15,
            rating = 4,
            remainingHours = 0,
            info = "Taller de manejo avanzado de herramientas de Office para estudiantes de todas las carreras, con enfoque en Excel y PowerPoint."
        ),
        Task(
            id = 6,
            title = "Evento de Bienvenida",
            location = "Plaza Central",
            date = "05/11/2024",
            startTime = "15:00",
            endTime = "18:00",
            totalHoursCompleted = null,
            isRecurring = false,
            recurrencePattern = null,
            currentParticipants = 20,
            maxParticipants = 30,
            rating = 3,
            remainingHours = 5,
            info = "Apoyo en la organización del evento de bienvenida para estudiantes de nuevo ingreso. Incluye montaje, atención a estudiantes y logística general."
        ),
        Task(
            id = 7,
            title = "Sesión de Tutoría",
            location = "Sala de Tutorías",
            date = "06/11/2024",
            startTime = "13:00",
            endTime = "15:00",
            totalHoursCompleted = null,
            isRecurring = true,
            recurrencePattern = "Quincenal",
            currentParticipants = 3,
            maxParticipants = 10,
            rating = 5,
            remainingHours = 25,
            info = "Sesión de tutoría para estudiantes de primer año, enfocada en mejorar las técnicas de estudio y aclarar dudas de las materias iniciales."
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
