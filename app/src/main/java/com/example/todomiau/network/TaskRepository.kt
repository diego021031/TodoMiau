package com.example.todomiau.network

import com.example.todomiau.core.ResultWrapper
import com.example.todomiau.core.safeCall
import com.example.todomiau.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val taskCollection = firestore.collection("Tasks")

    suspend fun createTask(task: Task): ResultWrapper<Void?> = safeCall {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun getAllTasks(): ResultWrapper<List<Task>> = safeCall {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw Exception("Usuario no autenticado")

        val snapshot = taskCollection
            .whereEqualTo("userId", currentUserId)
            .get()
            .await()

        val tasks = snapshot.documents.mapNotNull { doc ->
            try {
                Task(
                    id = doc.getString("id") ?: "",
                    name = doc.getString("name") ?: "",
                    description = doc.getString("description") ?: "",
                    date = doc.getDate("date") ?: Date(),
                    userId = doc.getString("userId") ?: ""
                )
            } catch (e: Exception) {
                null
            }
        }

        tasks
    }

    suspend fun updateTask(task: Task): ResultWrapper<Void?> = safeCall {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun deleteTask(taskId: String): ResultWrapper<Void?> = safeCall {
        taskCollection.document(taskId).delete().await()
    }
}
