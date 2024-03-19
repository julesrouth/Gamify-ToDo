package com.example.todofromscratch

import com.example.todofromscratch.model.domain.Task

class Tasks private constructor() {

    companion object {

        @Volatile private var instance: Tasks? = null // Volatile modifier is necessary

        fun getInstance() =
            instance ?: synchronized(this) { // synchronized to avoid concurrency problem
                instance ?: Tasks().also { instance = it }
            }
    }

    private val tasks: ArrayList<Task> = ArrayList()

    fun addTask(newTask: Task) {
        println("adding task into Tasks in Kotlin: " + newTask.description)
        tasks.add(newTask)
    }

    fun getTasks() : ArrayList<Task> {
        return tasks
    }

    fun getTaskbyName(name: String) : Task? {
        var foundTask: Task? = null
        val searchTasks: ArrayList<Task> = ArrayList()
        for (task in searchTasks) {
            if (task.taskName == name) {
                foundTask = task
                break // Break the loop once the task is found
            }
        }
        return foundTask
    }
}