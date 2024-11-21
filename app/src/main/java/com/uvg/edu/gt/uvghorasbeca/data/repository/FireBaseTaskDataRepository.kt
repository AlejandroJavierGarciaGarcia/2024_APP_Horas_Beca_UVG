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
    private val tasksCollection = firestore.collection("TaskData")

    companion object {
        private const val TAG = "FirebaseTaskRepository"
    }

    // Retorna todos los tasks + un listener para actualizarse en caso que haya cambios
    override suspend fun getAllTasks(): Flow<List<Task>> = callbackFlow {
        Log.d(TAG, "Listening for task updates.")
        val listener = tasksCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG, "Error fetching tasks: ${error.message}", error)
                close(error)
                return@addSnapshotListener
            }

            // Aqui setea el ID al ID del documento en FireStore, esto fue temporal y lo voy
            // a quitar despues.
            if (snapshot != null && !snapshot.isEmpty) {
                val tasks = snapshot.documents.mapNotNull { document ->
                    try {
                        document.toObject<Task>()?.apply {
                            id = document.id
                        }
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

    // Retorna task por ID
    override suspend fun getTaskById(taskId: String): Task? {
        return try {
            val task = tasksCollection.document(taskId).get().await().toObject<Task>()?.apply {
                id = taskId
            }
            task
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching task by ID: $taskId", e)
            null
        }
    }

    suspend fun addTask(task: Task): Boolean {
        // Falta la logica
        return true
    }

    suspend fun updateTask(taskId: String, updates: Map<String, Any>): Boolean {
        // Falta la logica, se puede buscar con ID igual que una de las funciones
        // de antes y modificar los parametros CREO
        return true
    }

    suspend fun deleteTask(taskId: String): Boolean {
        // Falta la logica, igual se puede buscar por ID pero no estoy seguro.
        return true
    }
}