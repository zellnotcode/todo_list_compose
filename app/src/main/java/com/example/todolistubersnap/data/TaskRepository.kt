package com.example.todolistubersnap.data

import com.example.todolistubersnap.domain.ITaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) : ITaskRepository {

    override fun getAllTask(): Flow<List<Task>> = taskDao.getAllTask()

    override fun getTaskById(id: Int): Flow<Task> = taskDao.getTaskById(id)

    override suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}