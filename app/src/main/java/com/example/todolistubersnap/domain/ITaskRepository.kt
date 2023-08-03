package com.example.todolistubersnap.domain

import com.example.todolistubersnap.data.Task
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {

    fun getAllTask(): Flow<List<Task>>

    fun getTaskById(id: Int): Flow<Task>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)
}