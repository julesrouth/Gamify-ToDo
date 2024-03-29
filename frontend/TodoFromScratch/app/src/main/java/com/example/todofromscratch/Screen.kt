package com.example.todofromscratch

sealed class Screen (val route: String){
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object MainScreen : Screen("main_screen")
    object AddTaskScreen : Screen("add_task_screen")

    object GameMainScreen : Screen("game_main_screen")
    object ShopScreen : Screen("shop_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")

            }
        }
    }
}