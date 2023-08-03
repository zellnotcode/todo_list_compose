package com.example.todolistubersnap.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todolistubersnap.MainDispatcherRule
import com.example.todolistubersnap.data.Task
import com.example.todolistubersnap.domain.ITaskUseCase
import com.example.todolistubersnap.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TaskViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockTaskUseCase: ITaskUseCase

    @Test
    fun `test getAllTasks success`() = runTest {
        val tasks = listOf(Task(1, "Task 1", "Tes", 20), Task(2, "Task 2", "Tes", 30))
        val viewModel = TaskViewModel(mockTaskUseCase)

        `when`(mockTaskUseCase.getAllTask()).thenReturn(MutableStateFlow(tasks))

        viewModel.getAllTasks()

        val observer = viewModel.tasksState.first()

        assertEquals(Status.Success(tasks), observer)
    }

    @Test
    fun `test getAllTasks error`() = runTest {
        val errorMessage = "Error Fetching"
        val viewModel = TaskViewModel(mockTaskUseCase)

        `when`(mockTaskUseCase.getAllTask()).thenReturn(flow {
            throw Exception(errorMessage)
        })

        viewModel.getAllTasks()

        val observer = viewModel.tasksState.first()

        assertEquals(Status.Error(errorMessage), observer)
    }
}