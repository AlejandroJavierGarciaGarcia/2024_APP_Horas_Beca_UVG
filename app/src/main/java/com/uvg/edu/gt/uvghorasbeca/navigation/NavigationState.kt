package com.uvg.edu.gt.uvghorasbeca.navigation

sealed class NavigationState(val route: String) {

    // Ruta para Login
    object LoginScreen : NavigationState("login_screen")

    // Rutas para convocatorias disponibles
    object AvailableTasks : NavigationState("available_tasks") // Usuario
    object AdminTasks : NavigationState("admin_tasks")         // Admin

    // Rutas para detalles de convocatoria
    object TaskDetails : NavigationState("task_details")                // Usuario
    object AdminTaskDetails : NavigationState("admin_task_details")     // Admin

    // Ruta para editar convocatorias (solo Admin)
    object EditTask : NavigationState("edit_task")

    // Rutas para horas pendientes e historial de horas beca
    object PendingHours : NavigationState("pending_hours")        // Mis horas pendientes
    object HoursHistory : NavigationState("hours_history")        // Historial de horas beca

    // Ruta para perfil y progreso
    object ProfileProgress : NavigationState("profile_progress")
}
