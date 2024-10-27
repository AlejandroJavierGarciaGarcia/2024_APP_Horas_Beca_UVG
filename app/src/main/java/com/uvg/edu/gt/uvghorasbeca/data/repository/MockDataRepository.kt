package com.uvg.edu.gt.uvghorasbeca.data.repository

import com.uvg.edu.gt.uvghorasbeca.data.models.Task

object MockDataRepository {

    // Función para obtener todas las tareas
    fun getAllTasks(): List<Task> {
        return TaskData.tasks
    }

    // Función para obtener una tarea por ID
    fun getTaskById(id: Int): Task? {
        return TaskData.tasks.find { it.id == id }
    }

    // Función para agregar una nueva tarea
    fun addTask(task: Task) {
        // Convertir la lista de tareas en mutable y agregar la nueva tarea
        val mutableTasks = TaskData.tasks.toMutableList()
        mutableTasks.add(task)
        TaskData.tasks = mutableTasks
    }

    // Función para actualizar una tarea existente
    fun updateTask(updatedTask: Task) {
        // Convertir la lista de tareas en mutable y reemplazar la tarea actualizada
        val mutableTasks = TaskData.tasks.toMutableList()
        val index = mutableTasks.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            mutableTasks[index] = updatedTask
            TaskData.tasks = mutableTasks
        }
    }

    // Función para eliminar una tarea
    fun deleteTask(id: Int) {
        val mutableTasks = TaskData.tasks.toMutableList()
        mutableTasks.removeAll { it.id == id }
        TaskData.tasks = mutableTasks
    }
}
