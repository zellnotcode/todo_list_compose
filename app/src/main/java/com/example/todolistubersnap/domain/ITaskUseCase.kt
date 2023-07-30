package com.example.todolistubersnap.domain

import com.example.todolistubersnap.data.Task
import com.example.todolistubersnap.utils.Status
import kotlinx.coroutines.flow.Flow

interface ITaskUseCase {

    fun getAllTask(): Flow<Status<List<Task>>>

    fun getTaskById(id: Int): Flow<Status<Task>>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)
}