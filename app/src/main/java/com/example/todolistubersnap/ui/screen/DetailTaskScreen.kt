package com.example.todolistubersnap.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todolistubersnap.R
import com.example.todolistubersnap.data.Task
import com.example.todolistubersnap.ui.component.ShowDatePicker
import com.example.todolistubersnap.ui.viewmodel.TaskViewModel
import com.example.todolistubersnap.utils.Status
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DetailTaskScreen(navController: NavHostController, taskViewModel: TaskViewModel, taskId: Int) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("Not Selected") }
    var dueDateMillis by remember { mutableStateOf(0L) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(taskId) {
        taskViewModel.getTaskById(taskId)

        taskViewModel.selectedTaskState.collect {taskState ->
            when (taskState) {
                is Status.Loading -> {

                }

                is Status.Success -> {
                    val task = taskState.data
                    if (task != null) {
                        taskName = task.title
                        taskDescription = task.description
                        selectedDate =
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.dueDateMillis)
                        dueDateMillis = task.dueDateMillis
                    }
                }

                is Status.Error -> {}
            }
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.add_task),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    },
        floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (taskName.isNotBlank() && taskDescription.isNotBlank() && dueDateMillis != 0L) {
                            val task = Task(taskId, taskName, taskDescription, dueDateMillis)
                            taskViewModel.updateTask(task)
                            navController.popBackStack()
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.add_task)
                    )
                }
        }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = taskName,
                onValueChange = { taskName = it
                    Log.e("TES", it)
                },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.task_name
                        )
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.description
                        )
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = selectedDate)

            Spacer(modifier = Modifier.height(16.dp))

            ShowDatePicker(onDateSelected = { dateInMillis ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(dateInMillis)
                selectedDate = formattedDate
                dueDateMillis = dateInMillis
            })

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val taskReady = Task(taskId, taskName, taskDescription, dueDateMillis)
                    taskViewModel.deleteTask(taskReady)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}