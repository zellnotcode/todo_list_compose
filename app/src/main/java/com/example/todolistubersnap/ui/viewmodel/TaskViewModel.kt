package com.example.todolistubersnap.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistubersnap.data.Task
import com.example.todolistubersnap.domain.ITaskUseCase
import com.example.todolistubersnap.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val iTaskUseCase: ITaskUseCase) : ViewModel() {

    private val _tasksState = MutableStateFlow<Status<List<Task>>>(Status.Loading)
    val tasksState = _tasksState.asStateFlow()

    private val _selectedTaskState = MutableStateFlow<Status<Task>>(Status.Loading)
    val selectedTaskState = _selectedTaskState.asStateFlow()

    private val _showSnackBar = MutableStateFlow<String?>(null)
    val showSnackBar = _showSnackBar.asStateFlow()



    fun getAllTasks() {
        viewModelScope.launch {
            try {
                _tasksState.value = Status.Loading

                iTaskUseCase.getAllTask().collect {
                    _tasksState.value = Status.Success(it)
                }

            } catch (e: Exception) {
                _tasksState.value = Status.Error("Error Fetching")
            }
        }
    }

    fun getTaskById (id: Int) {
        viewModelScope.launch {
            try {
                _selectedTaskState.value = Status.Loading

                iTaskUseCase.getTaskById(id).collect {
                    _selectedTaskState.value = Status.Success(it)
                }

            } catch (e: Exception) {
                _selectedTaskState.value = Status.Error("Error Fetching")
            }
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            try {
                iTaskUseCase.insertTask(task)
                _showSnackBar.value = "Task inserted successfully"
            } catch (e: Exception) {
                _showSnackBar.value = "Error inserting task"
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                iTaskUseCase.deleteTask(task)
                _showSnackBar.value = "Task deleted successfully"
            } catch (e: Exception) {
                _showSnackBar.value = "Error deleting task"
            }
        }
    }

    fun onSnackBarShown() {
        _showSnackBar.value = null
    }
}