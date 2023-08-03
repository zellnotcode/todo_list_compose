package com.example.todolistubersnap.di

import android.content.Context
import androidx.room.Room
import com.example.todolistubersnap.data.TaskDao
import com.example.todolistubersnap.data.TaskDatabase
import com.example.todolistubersnap.data.TaskRepository
import com.example.todolistubersnap.domain.ITaskRepository
import com.example.todolistubersnap.domain.ITaskUseCase
import com.example.todolistubersnap.domain.TaskInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TaskModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, "db_task").build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideITaskRepository(taskDao: TaskDao): ITaskRepository {
        return TaskRepository(taskDao)
    }

    @Provides
    @Singleton
    fun provideITaskUseCase(repository: ITaskRepository) : ITaskUseCase {
        return TaskInteractor(repository)
    }
}