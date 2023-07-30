package com.example.todolistubersnap.domain

import com.example.todolistubersnap.data.Task
import com.example.todolistubersnap.data.TaskRepository
import com.example.todolistubersnap.utils.Status
import kotlinx.coroutines.flow.Flow

class TaskInteractor(private val repository: TaskRepository) : ITaskUseCase {
    override fun getAllTask(): Flow<Status<List<Task>>> = repository.getAllTask()

    override fun getTaskById(id: Int): Flow<Status<Task>> = repository.getTaskById(id)

    override suspend fun insertTask(task: Task) = repository.insertTask(task)

    override suspend fun deleteTask(task: Task) = repository.deleteTask(task)
}