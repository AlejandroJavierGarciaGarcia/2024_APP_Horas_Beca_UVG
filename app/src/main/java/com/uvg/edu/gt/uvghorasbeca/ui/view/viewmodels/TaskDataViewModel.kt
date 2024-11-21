package com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels

import android.util.Log
import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.edu.gt.uvghorasbeca.data.repository.FirebaseTaskDataRepository
import com.uvg.edu.gt.uvghorasbeca.data.repository.TaskDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TaskDataViewModel() : ViewModel() {
    private val repository : TaskDataRepository = FirebaseTaskDataRepository()
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            repository.getAllTasks()
                .catch { e -> e.printStackTrace() } // Handle errors
                .collectLatest { taskList ->
                    _tasks.value = taskList
                }
        }
    }

    fun selectTask(task: Task?) {
        _selectedTask.value = task
    }
}
