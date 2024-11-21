package com.uvg.edu.gt.uvghorasbeca.data.repository

import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataRepository {
    suspend fun getAllTasks(): Flow<List<Task>>
    suspend fun getTaskById(taskId: String): Task?
}