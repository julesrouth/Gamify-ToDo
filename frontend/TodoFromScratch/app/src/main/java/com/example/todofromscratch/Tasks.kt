package com.example.todofromscratch

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
        tasks.add(newTask)
    }

    fun getTasks() : ArrayList<Task> {
        return tasks
    }
}