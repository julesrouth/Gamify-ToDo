package com.example.todofromscratch.model.domain;

import com.example.todofromscratch.model.net.Difficulty;
import com.example.todofromscratch.model.net.TaskType;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {

//    public int taskId; //TODO DO I NEED THE TASKID HERE????
    public String taskName;
    public String description;
    public String dueDate;
    public String difficulty;
    public String type;
    public String userId;
    public Boolean completed;

    public Task() {
    }

    public Task(String taskName, String description, String dueDate,
                String difficulty, String type, String userId, Boolean completed) {
//        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.difficulty = difficulty;
        this.type = type;
        this.userId = userId;
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return
//                Objects.equals(taskName, task.taskName) &&
                Objects.equals(description, task.description) &&
                Objects.equals(dueDate, task.dueDate) &&
                Objects.equals(difficulty, task.difficulty) &&
                Objects.equals(type, task.type) &&
                Objects.equals(userId, task.userId) &&
                Objects.equals(completed, task.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, dueDate, difficulty, type, userId, completed);
    }

    @Override
    public String toString() {
        return "Status{" +
//                "taskId='" + taskId + '\'' +
                ", taskName=" + taskName +
                ", description=" + description +
                ", dueDate=" + dueDate +
                ", difficulty=" + difficulty +
                ", type=" + type +
                ", userId=" + userId +
                ", completed=" + completed +
                '}';
    }

}
