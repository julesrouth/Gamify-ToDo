package com.example.todofromscratch

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                    })
            }
            composable(route = Screen.MainScreen.route) {
                MainScreen(
                    onAddTaskButtonClicked = {
                        navController.navigate(Screen.AddTaskScreen.route)
                    })
            }
            composable(
                route = Screen.AddTaskScreen.route,
            ) {
                AddTaskScreen(
                    onNextButtonClicked = {
                        navController.navigate(Screen.MainScreen.route)
                })
            }
        }
    }
}