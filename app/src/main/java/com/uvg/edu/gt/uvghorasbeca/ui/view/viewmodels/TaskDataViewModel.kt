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

class TaskDataViewModel(private val authViewModel: AuthViewModel) : ViewModel() {
    private val repository: TaskDataRepository = FirebaseTaskDataRepository()

    // StateFlow for all tasks
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks

    // StateFlow for available tasks (unassigned)
    private val _availableTasks = MutableStateFlow<List<Task>>(emptyList())
    val availableTasks: StateFlow<List<Task>> = _availableTasks

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    init {
        fetchAllTasks()
    }

    private fun fetchAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks()
                .catch { e ->
                    Log.e("TaskDataViewModel", "Error fetching all tasks: ${e.message}", e)
                }
                .collectLatest { taskList ->
                    Log.d("TaskDataViewModel", "Fetched ${taskList.size} tasks from Firestore.")
                    _allTasks.value = taskList
                }
        }
    }

    fun fetchAvailableTasks() {
        viewModelScope.launch {
            repository.getAllTasks()
                .catch { e ->
                    Log.e("TaskDataViewModel", "Error fetching available tasks: ${e.message}", e)
                }
                .collectLatest { taskList ->
                    val assignedActivityIds = authViewModel.getAssignedActivities()
                    val unassignedTasks = taskList.filter { it.id.toString() !in assignedActivityIds }
                    Log.d("TaskDataViewModel", "Available tasks count: ${unassignedTasks.size}")
                    _availableTasks.value = unassignedTasks
                }
        }
    }

    fun selectTask(task: Task?) {
        Log.d("TaskDataViewModel", "Selected Task: ${task?.title ?: "None"}")
        _selectedTask.value = task
    }
}

