package com.example.todofromscratch

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todofromscratch.ui.AdventureScreen
import com.example.todofromscratch.ui.CharacterScreen
import com.example.todofromscratch.ui.GameMainScreen
import com.example.todofromscratch.ui.ShopScreen
import androidx.navigation.navArgument
import com.example.todofromscratch.cache.Cache
import com.example.todofromscratch.game.Game
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
//import com.onesignal.OSNotificationOpenedResult
//import com.onesignal.OSNotification

/*
Next steps:
    Add completion time
    Edit tasks by clicking on them
 */

// NOTE: Replace the below with your own ONESIGNAL_APP_ID
const val ONESIGNAL_APP_ID = "913ea4fc-8967-4cbb-9d67-19f7d3a25957"

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            TodoFromScratchTheme {
                Navigation()
            }
        }

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)


        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
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
//                           navController.navigate(Screen.GameMainScreen.route)
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
            composable(route = Screen.MainScreen.route) {
                MainScreen(
                    onAddTaskButtonClicked = {
                        navController.navigate(Screen.AddTaskScreen.route)
                    },
                    onTaskClicked = { task ->
                        // Navigate to AddTaskScreen with task information
                        navController.navigate("${Screen.AddTaskScreen.route}/${task.taskId}")
                    },
                    onMenuButtonClicked = {
                        Cache.getInstance().currPlayer = null
                        navController.navigate(Screen.GameMainScreen.route)
                    },
                    onEditTaskButtonClicked = { task ->
                        navController.navigate(Screen.AddTaskScreen.createRoute(task.taskId))
                    },
                    onLogoutClicked = {
                        navController.popBackStack(Screen.LoginScreen.route, false)
                        Cache.getInstance().clearCache()
                    }
                )
            }
            composable(
                route = Screen.AddTaskScreen.route
            ) {
                AddTaskScreen(
                    onNextButtonClicked = {
                        navController.popBackStack()
                    }, navController = navController
                )
            }
            composable(
                route = Screen.GameMainScreen.route
            ) {
                GameMainScreen(
                    onAdventureClicked = {
                        navController.navigate(Screen.AdventureScreen.route)
                    },
                    onShopClicked = {
                        navController.navigate(Screen.ShopScreen.route)
                    },
                    onCharacterClicked = {
                        navController.navigate(Screen.CharacterScreen.route)
                    },
                    onExitClicked = {
                        navController.navigateUp()
                    },
                    game = Cache.getInstance().game

                )
            }
            composable(
                route = Screen.ShopScreen.route
            ) {
                ShopScreen(
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = Screen.CharacterScreen.route
            ) {
                CharacterScreen(
                    onBackButtonClicked = {
                        navController.navigateUp()
                    }
                )
        }
            composable(
                route = Screen.AdventureScreen.route
            ) {
                AdventureScreen(
                )
            }

//            composable(
//                route = "${Screen.AddTaskScreen.route}/{taskId}",
//                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
//            ) { backStackEntry ->
//                val taskId = backStackEntry.arguments?.getInt("taskId")
//                val task = taskId?.let { Tasks.getInstance().getTaskById(it) }
//                AddTaskScreen(
//                    taskToUpdate = task,
//                    onNextButtonClicked = {
//                        navController.popBackStack()
//                    }, navController = navController
//                )
//            }
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