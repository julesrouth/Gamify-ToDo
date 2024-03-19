package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.net.Difficulty;
import com.example.todofromscratch.model.net.TaskType;

public class AddTaskRequest {

    private authtoken authtoken;
    private Task task;

    private AddTaskRequest() {}

    public AddTaskRequest(authtoken authtoken, Task task) {
        this.authtoken = authtoken;
        this.task = task;
    }

    public authtoken getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(authtoken authtoken) {
        this.authtoken = authtoken;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
