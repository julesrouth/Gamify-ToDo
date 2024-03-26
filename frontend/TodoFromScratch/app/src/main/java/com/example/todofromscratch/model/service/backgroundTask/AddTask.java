package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.net.TweeterRemoteException;
import com.example.todofromscratch.model.net.request.AddTaskRequest;
import com.example.todofromscratch.model.net.response.AddTaskResponse;
import com.example.todofromscratch.model.service.TaskService;

import java.io.IOException;

public class AddTask extends AuthenticatedTask {

    /**
     * The new status being sent. Contains all properties of the status,
     * including the identity of the user sending the status.
     */
    private final Task task;

    public AddTask(AuthToken authToken, Task task, Handler messageHandler) {
        super(authToken, messageHandler);
        this.task = task;
    }

    private static final String LOG_TAG = "GET_ADD_TASK";
    public static final String ADD_TASK_KEY = "addtask";

    @Override
    protected void runTask() {
        try {
            AuthToken authtoken = Cache.getInstance().getCurrUserAuthToken();
            System.out.println("in run task for AddTask");
            System.out.println("Task4 difficulty: " + task.difficulty);
            System.out.println("Task4 taskType: " + task.type);
            System.out.println("Task4 completed: " + task.completed);
            AddTaskRequest request = new AddTaskRequest(authtoken, task);
            System.out.println("Authtoken in AddTask: " + authtoken);
            System.out.println("Task in AddTask: " + task.taskName);
            System.out.println("TaskID in ADDTASK: " + task.taskId);
            AddTaskResponse response = getServerFacade().addTask(request, TaskService.GET_ADDTASK_URL);
            System.out.println("TaskID in ADDTASK response: " + response.getTask().taskId);
            task.setTaskId(response.getTask().taskId);
            System.out.println("TaskID after setting from response: " + task.taskId);

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
