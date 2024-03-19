package com.example.todofromscratch

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme

/*
Next steps:
    Add completion time
    Edit tasks by clicking on them
 */

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoFromScratchTheme {
                Navigation()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Navigation() {

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.LoginScreen.route

        ) {
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(
                    onLoginButtonClicked = {
                        navController.navigate(Screen.MainScreen.route)
                    },
                    onRegisterClicked = {
                        navController.navigate(Screen.RegisterScreen.route)
                    }
                )
            }
            composable(route = Screen.RegisterScreen.route) {
                RegisterScreen(
                    onRegisterButtonClicked = {
                        navController.navigate(Screen.MainScreen.route)
                    },
                    onLoginClicked = {
                        // Navigate back to LoginScreen when cancel is clicked
                        navController.popBackStack()
                    }
                )
            }
//            composable(route = Screen.MainScreen.route) {
//                MainScreen(
//                    onAddTaskButtonClicked = {
//                        navController.navigate(Screen.AddTaskScreen.route)
//                    })
//            }
            composable(route = Screen.MainScreen.route) {
                MainScreen(
                    onAddTaskButtonClicked = {
                        navController.navigate(Screen.AddTaskScreen.route)
                    },
                    onTaskClicked = { task ->
                        // Navigate to AddTaskScreen with task information
                        navController.navigate("${Screen.AddTaskScreen.route}/${task.taskName}")
                    }
                )
            }
//            composable(route = Screen.AddTaskScreen.route) {
//                AddTaskScreen(
//                    onNextButtonClicked = {
//                        navController.popBackStack()
//                    }
//                )
//            }
//            composable(
//                route = "${Screen.AddTaskScreen.route}/{taskName}",
//                arguments = listOf(navArgument("taskName") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val taskName = backStackEntry.arguments?.getString("taskName")
//                // Convert taskId to task object using your data source (Tasks.getInstance().getTaskById(taskId))
//                val task = taskName?.let { Tasks.getInstance().getTaskbyName(it) }
//                AddTaskScreen(
//                    taskToUpdate = task,
//                    onNextButtonClicked = {
//                        navController.popBackStack()
//                    }
//                )
//            }
            composable(
                route = Screen.AddTaskScreen.route
            ) {
                AddTaskScreen(
                    onNextButtonClicked = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = "${Screen.AddTaskScreen.route}/{taskName}",
                arguments = listOf(navArgument("taskName") { type = NavType.StringType })
            ) { backStackEntry ->
                val taskName = backStackEntry.arguments?.getString("taskName")
                val task = taskName?.let { Tasks.getInstance().getTaskbyName(it) }
                AddTaskScreen(
                    taskToUpdate = task,
                    onNextButtonClicked = {
                        navController.popBackStack()
                    }
                )
            }
//            composable(
//                route = Screen.AddTaskScreen.route,
//            ) {
//                AddTaskScreen(
//                    onNextButtonClicked = {
//                        navController.navigate(Screen.MainScreen.route)
//                })
//            }
        }
    }
}