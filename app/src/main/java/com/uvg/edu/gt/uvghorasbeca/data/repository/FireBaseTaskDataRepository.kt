package com.uvg.edu.gt.uvghorasbeca.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.uvg.edu.gt.uvghorasbeca.data.models.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseTaskDataRepository : TaskDataRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val tasksCollection = firestore.collection("TaskData") // Collection name

    companion object {
        private const val TAG = "FirebaseTaskRepository"
    }

    override suspend fun getAllTasks(): Flow<List<Task>> = callbackFlow {
        Log.d(TAG, "Listening for task updates.")
        val listener = tasksCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG, "Error fetching tasks: ${error.message}", error)
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val tasks = snapshot.documents.mapNotNull { document ->
                    try {
                        document.toObject<Task>()?.apply { id = 1 }  // Set id to "1" for all tasks
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing task document: ${document.id}", e)
                        null
                    }
                }
                Log.d(TAG, "Fetched ${tasks.size} tasks successfully.")
                trySend(tasks).isSuccess
            } else {
                Log.w(TAG, "No tasks found.")
                trySend(emptyList()).isSuccess
            }
        }

        awaitClose {
            Log.d(TAG, "Task updates listener removed.")
            listener.remove()
        }
    }

    override suspend fun getTaskById(taskId: String): Task? {
        return try {
            Log.d(TAG, "Fetching task with ID: $taskId")
            val task = tasksCollection.document(taskId).get().await().toObject<Task>()?.apply { id = 1 }
            if (task != null) {
                Log.d(TAG, "Task fetched successfully: ${task.title}")
            } else {
                Log.w(TAG, "Task with ID $taskId not found.")
            }
            task
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching task by ID: $taskId", e)
            null
        }
    }

    suspend fun addTask(task: Task): Boolean {
        return try {
            Log.d(TAG, "Adding new task: ${task.title}")
            tasksCollection.add(task).await()
            Log.d(TAG, "Task added successfully.")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error adding task: ${task.title}", e)
            false
        }
    }

    suspend fun updateTask(taskId: String, updates: Map<String, Any>): Boolean {
        return try {
            Log.d(TAG, "Updating task with ID: $taskId")
            tasksCollection.document(taskId).update(updates).await()
            Log.d(TAG, "Task updated successfully.")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error updating task with ID: $taskId", e)
            false
        }
    }

    suspend fun deleteTask(taskId: String): Boolean {
        return try {
            Log.d(TAG, "Deleting task with ID: $taskId")
            tasksCollection.document(taskId).delete().await()
            Log.d(TAG, "Task deleted successfully.")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting task with ID: $taskId", e)
            false
        }
    }
}
