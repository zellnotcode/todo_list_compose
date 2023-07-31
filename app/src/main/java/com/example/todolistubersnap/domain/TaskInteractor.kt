package com.example.todolistubersnap.domain

import com.example.todolistubersnap.data.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskInteractor @Inject constructor(private val repository: ITaskRepository) : ITaskUseCase {
    override fun getAllTask(): Flow<List<Task>> = repository.getAllTask()

    override fun getTaskById(id: Int): Flow<Task> = repository.getTaskById(id)

    override suspend fun insertTask(task: Task) = repository.insertTask(task)

    override suspend fun deleteTask(task: Task) = repository.deleteTask(task)
}