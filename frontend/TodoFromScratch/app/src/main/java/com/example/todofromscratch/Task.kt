package com.example.todofromscratch

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class Task(

    val name: String = "",
    val date: String = "",

) {
    var completed: Boolean = false
}