package com.example.todolistubersnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolistubersnap.ui.screen.AddTaskScreen
import com.example.todolistubersnap.ui.screen.DetailTaskScreen
import com.example.todolistubersnap.ui.screen.TaskListScreen
import com.example.todolistubersnap.ui.theme.TodoListUbersnapTheme
import com.example.todolistubersnap.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListUbersnapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val taskViewModel: TaskViewModel = hiltViewModel()
                    NavHost(navController = navController, startDestination = "homeScreen") {
                        composable("homeScreen") {
                            TodoAppScreen(navController, taskViewModel)
                        }
                        composable("addTaskScreen") {
                            AddTaskScreen(navController, taskViewModel)
                        }
                        composable("detail/{taskId}") { backStackEntry ->
                            val taskId =
                                backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                            if (taskId != null) {
                                DetailTaskScreen(
                                    navController = navController,
                                    taskViewModel = taskViewModel,
                                    taskId = taskId
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppScreen(
    navHostController: NavHostController,
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Todo App",
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
                    navHostController.navigate("addTaskScreen")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add FAB",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        TaskListScreen(
            navController = navHostController,
            taskViewModel,
            paddingValues = paddingValues
        )
    }
}