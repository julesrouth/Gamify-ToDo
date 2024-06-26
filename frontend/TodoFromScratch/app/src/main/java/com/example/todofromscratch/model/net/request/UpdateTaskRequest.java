package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.AuthToken;

public class UpdateTaskRequest {

    private AuthToken authtoken;
    private Task task;

    private UpdateTaskRequest() {}

    public UpdateTaskRequest(AuthToken authtoken, Task task) {
        this.authtoken = authtoken;
        this.task = task;
    }

    public AuthToken getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(AuthToken authtoken) {
        this.authtoken = authtoken;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
