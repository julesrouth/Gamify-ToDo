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

    private var tasks: ArrayList<Task> = ArrayList()

    fun setTasks(currentTasks: ArrayList<Task>) {
        tasks = currentTasks
    }
    fun addTask(newTask: Task) {
        println("adding task into Tasks in Kotlin: " + newTask.description)
        tasks.add(newTask)
    }

    fun getTasks() : ArrayList<Task> {
        return tasks
    }

    fun deleteTask(id: Int): ArrayList<Task> {
        val iterator = tasks.iterator()
        while (iterator.hasNext()) {
            val task = iterator.next()
            if (task.taskId == id) {
                iterator.remove()
            }
        }
        return tasks
    }

    fun updateTask(id: Int, updatedTask: Task) {
        for (index in tasks.indices) {
            val task = tasks[index]
            if (task.taskId == id) {
                tasks[index] = updatedTask
            }
        }
    }

    fun getTaskById(id: Int) : Task? {
        var foundTask: Task? = null
        for (task in tasks) {
            if (task.taskId == id) {
                foundTask = task
            }
        }
        return foundTask
    }
}