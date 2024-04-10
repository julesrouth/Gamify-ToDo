package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.net.TweeterRemoteException;
import com.example.todofromscratch.model.net.request.AddTaskRequest;
import com.example.todofromscratch.model.net.request.DeleteTaskRequest;
import com.example.todofromscratch.model.net.request.UpdateTaskRequest;
import com.example.todofromscratch.model.net.response.AddTaskResponse;
import com.example.todofromscratch.model.net.response.DeleteTaskResponse;
import com.example.todofromscratch.model.net.response.UpdateTaskResponse;
import com.example.todofromscratch.model.service.TaskService;

import java.io.IOException;

public class DeleteTask extends AuthenticatedTask {

    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private final int taskId;

    public DeleteTask(AuthToken authToken, int taskId, Handler messageHandler) {
        super(authToken, messageHandler);
        this.taskId = taskId;
    }

    private static final String LOG_TAG = "GET_UPDATE_TASK";
    public static final String ADD_TASK_KEY = "updatetask";

    @Override
    protected void runTask() {
        try {
            AuthToken authtoken = Cache.getInstance().getCurrUserAuthToken();
            System.out.println("in run task for DeleteTask");
            DeleteTaskRequest request = new DeleteTaskRequest(authtoken, taskId);
            DeleteTaskResponse response = getServerFacade().deleteTask(request, TaskService.GET_DELETETASK_URL);
            System.out.println("TaskID after setting from response: " + this.taskId);

            if (response.isSuccess()) {
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to post Status", ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
    }

}
