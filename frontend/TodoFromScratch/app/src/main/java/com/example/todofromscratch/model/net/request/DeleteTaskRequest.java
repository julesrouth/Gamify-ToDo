package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.AuthToken;

public class DeleteTaskRequest {

    private AuthToken authtoken;
    private int taskId;

    private DeleteTaskRequest() {}

    public DeleteTaskRequest(AuthToken authtoken, int taskId) {
        this.authtoken = authtoken;
        this.taskId = taskId;
    }

    public AuthToken getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(AuthToken authtoken) {
        this.authtoken = authtoken;
    }

    public int getId() {
        return taskId;
    }

    public void setTaskId(int id) {
        this.taskId = id;
    }
}
