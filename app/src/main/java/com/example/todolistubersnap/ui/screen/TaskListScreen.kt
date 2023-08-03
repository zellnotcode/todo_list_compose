package com.example.todolistubersnap.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todolistubersnap.data.Task
import com.example.todolistubersnap.ui.component.TaskItem
import com.example.todolistubersnap.ui.viewmodel.TaskViewModel
import com.example.todolistubersnap.utils.Status

@Composable
fun TaskListScreen(navController: NavHostController, taskViewModel: TaskViewModel, paddingValues: PaddingValues) {
    val taskState by taskViewModel.tasksState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (taskState) {
            is Status.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(top = 10.dp)
                        )
                    }
                }
            }

            is Status.Success -> {
                items((taskState as Status.Success<List<Task>>).data) { task ->
                    TaskItem(
                        name = task.title,
                        dueDate = task.dueDateMillis,
                        modifier = Modifier.clickable {
                            navController.navigate("detail/${task.id}")
                        })
                }
            }

            is Status.Error -> {
                item {
                    Text(
                        text = (taskState as Status.Error).error,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}