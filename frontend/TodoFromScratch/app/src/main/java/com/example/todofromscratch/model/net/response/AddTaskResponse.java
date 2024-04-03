package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.Player;
import com.example.todofromscratch.model.domain.Task;

public class AddTaskResponse extends Response {

//    public AddTaskResponse(String message) {
//        super(false, message);
//    }
//
//    public AddTaskResponse() {
//        super(true, null);
//    }

    Task task;

    public AddTaskResponse(Task task, String message) {
        super(true, null);
        this.task = task;
    }

    public AddTaskResponse(String message) {
        super(false, message);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
