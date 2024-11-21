package com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels

import android.util.Log
import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.edu.gt.uvghorasbeca.data.repository.FirebaseTaskDataRepository
import com.uvg.edu.gt.uvghorasbeca.data.repository.TaskDataRepository
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de manejar informacion relacionada a los Tasks / Eventos de horas
 * beca.
 */
class TaskDataViewModel(private val authViewModel: AuthViewModel) : ViewModel() {
    private val repository: TaskDataRepository = FirebaseTaskDataRepository()

    // StateFlow para todos los tasks en la DB
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks

    // StateFlow para todos los tasks a los que no esta asignado el usuario
    private val _availableTasks = MutableStateFlow<List<Task>>(emptyList())
    val availableTasks: StateFlow<List<Task>> = _availableTasks

    // StateFlow para todos los tasks a los que esta asignado el usuario
    private val _assignedTasks = MutableStateFlow<List<Task>>(emptyList())
    val assignedTasks: StateFlow<List<Task>> = _assignedTasks

    // Selected Task (?)
    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    init {
        fetchAllTasks()
    }

    // Actualiza allTasks con informacion de la DB
    fun fetchAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks()
                .catch { e ->
                    Log.e("TaskDataViewModel", "Error fetching all tasks: ${e.message}", e)
                }
                .collectLatest { taskList ->
                    _allTasks.value = taskList
                }
        }
    }

    // Actualiza availableTasks con informacion de la DB
    fun fetchAvailableTasks() {
        viewModelScope.launch {
            repository.getAllTasks()
                .catch { e ->
                    Log.e("TaskDataViewModel", "Error fetching available tasks: ${e.message}", e)
                }
                // Filtra todos los ID's que no corresponden a la lista de Tasks asignados del usuario
                .collectLatest { taskList ->
                    val assignedActivityIds = authViewModel.getAssignedActivities()
                    val unassignedTasks = taskList.filter { it.id !in assignedActivityIds }
                    _availableTasks.value = unassignedTasks
                }
        }
    }

    // Actualiza assignedTasks con informacion de la DB
    fun fetchAssignedTasks() {
        viewModelScope.launch {
            repository.getAllTasks()
                .catch { e ->
                    Log.e("TaskDataViewModel", "Error fetching assigned tasks: ${e.message}", e)
                }
                // Filtra todos los ID's que corresponden a la lista de Tasks asignados del usuario
                .collectLatest { taskList ->
                    val assignedActivityIds = authViewModel.getAssignedActivities()
                    val assignedTasks = taskList.filter { it.id in assignedActivityIds }
                    Log.d("TaskDataViewModel", "Assigned tasks count: ${assignedTasks.size}")
                    _assignedTasks.value = assignedTasks
                }
        }
    }

    fun selectTask(task: Task?) {
        _selectedTask.value = task
    }
}

