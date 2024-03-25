package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.Player;
import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.net.ServerFacade;
import com.example.todofromscratch.model.net.request.CheckTaskRequest;
import com.example.todofromscratch.model.net.request.GetPlayerRequest;
import com.example.todofromscratch.model.net.response.CheckTaskResponse;
import com.example.todofromscratch.model.net.response.GetPlayerResponse;
import com.example.todofromscratch.model.service.TaskService;
import com.example.todofromscratch.model.service.UserService;

public class CheckTask extends AuthenticatedTask {

    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private final Task task;

    public CheckTask(AuthToken authToken, Task task, Handler messageHandler) {
        super(authToken, messageHandler);
        this.task = task;
    }

    private static final String GET_CHECKTASK_TAG = "GetCheckTask";
    public static final String CHECKTASK_KEY = "CheckTask";

    private ServerFacade serverFacade;

    @Override
    protected void runTask() {
        System.out.println("In runTask() in CheckTask");
        try {
            System.out.println("Authtoken: " + Cache.getInstance().getCurrUserAuthToken().userId);
            System.out.println("Task in runTask: " + task);
            System.out.println("TaskId in runTask: " + task.taskId);
            CheckTaskRequest request = new CheckTaskRequest(Cache.getInstance().getCurrUserAuthToken(), task.taskId);
            CheckTaskResponse response = getServerFacade().checkTask(request, TaskService.GET_CHECKTASK_URL);
            if (response.isSuccess()) {
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(GET_CHECKTASK_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(CHECKTASK_KEY, task);
    }

    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }

}
