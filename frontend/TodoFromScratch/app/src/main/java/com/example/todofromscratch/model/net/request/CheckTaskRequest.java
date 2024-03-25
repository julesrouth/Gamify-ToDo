package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.Task;

public class CheckTaskRequest {

    private AuthToken authtoken;
    private int taskId;

    private CheckTaskRequest() {}

    public CheckTaskRequest(AuthToken authtoken, int taskId) {
        this.authtoken = authtoken;
        this.taskId = taskId;
    }

    public AuthToken getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(AuthToken authtoken) {
        this.authtoken = authtoken;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

}
