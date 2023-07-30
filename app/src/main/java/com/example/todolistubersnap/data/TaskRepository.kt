package com.example.todolistubersnap.data

import android.content.Context
import com.example.todolistubersnap.domain.ITaskRepository
import com.example.todolistubersnap.utils.Status
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) : ITaskRepository {

    companion object {

        @Volatile
        private var INSTANCE: TaskRepository? = null

        fun getInstance(context: Context): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    val database = TaskDatabase.getDatabase(context)
                    INSTANCE = TaskRepository(database.taskDao())
                }
                return INSTANCE as TaskRepository
            }
        }
    }

    override fun getAllTask(): Flow<Status<List<Task>>> = taskDao.getAllTask()

    override fun getTaskById(id: Int): Flow<Status<Task>> = taskDao.getTaskById(id)

    override suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}