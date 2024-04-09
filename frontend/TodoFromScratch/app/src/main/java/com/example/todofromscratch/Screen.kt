package com.example.todofromscratch

import com.example.todofromscratch.model.domain.Task

sealed class Screen (val route: String){
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object MainScreen : Screen("main_screen")
    object AddTaskScreen : Screen("add_task_screen/{taskId}") {
        fun createRoute(id: Int): String {
            return "add_task_screen/${id.toInt()}"
        }
    }

    object GameMainScreen : Screen("game_main_screen")
    object ShopScreen : Screen("shop_screen")

    object AdventureScreen : Screen("adventure_screen")
    object CharacterScreen : Screen("character_screen")
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")

            }
        }
    }
}