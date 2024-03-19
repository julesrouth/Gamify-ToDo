package com.example.todofromscratch.model.service.backgroundTask;

import com.example.todofromscratch.Tasks;
import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.net.TweeterRemoteException;
import com.example.todofromscratch.model.net.request.TasksRequest;
import com.example.todofromscratch.model.net.response.TasksResponse;
import com.example.todofromscratch.model.service.TaskService;
import com.example.todofromscratch.util.Pair;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class TaskTask extends PagedTaskTask {

    private static final String LOG_TAG = "GET_TASK";
    public static final String TASKS_KEY = "tasks";
    public static final String MORE_PAGES_KEY = "more-pages";
    private boolean hasMorePages;
    private List<Task> tasks;
    private Task lastTask;
    private authtoken authtoken;

    public TaskTask(authtoken authtoken, User targetUser, int limit, Task lastTask,
                    Handler messageHandler) {
        super(authtoken, targetUser, limit, lastTask, messageHandler);
        this.authtoken = authtoken;
        this.lastTask = lastTask;
    }

    @Override
    protected void runTask() throws IOException {
//        Pair<List<Task>, Boolean> pageOfItems = getItems();
//
//        tasks = pageOfItems.getFirst();
//        hasMorePages = pageOfItems.getSecond();

        try {
            User targetUser = getTargetUser();
//            Status lastStatus = getLastItem();
            Task lastTask = null;
            if (getLastItem() != null) {
                lastTask = getLastItem();
            }

//            Task temp_task = new Task(taskname, description, dueDate, difficulty, type, userId, completed);
//            TaskRequest request = new TaskRequest(temp_task.taskName, temp_task.description, temp_task.dueDate,
//                    temp_task.difficulty, temp_task.type, temp_task.userId, temp_task.completed);

            User cacheUser = Cache.getInstance().getCurrUser();
            authtoken cacheAuthtoken = Cache.getInstance().getCurrUserAuthToken();
            System.out.println("cacheUser in TaskTask: " + cacheUser.getUsername());
            System.out.println("cacheAuthToken in TaskTask: " + cacheAuthtoken.token);
//            authtoken = new authtoken("nou3hfrayosw","RTW94TC2RH33");
            System.out.println("AuthToken before: " + cacheAuthtoken);
            TasksRequest request = new TasksRequest(cacheAuthtoken);
            System.out.println("AuthToken: " + cacheAuthtoken);
            TasksResponse response = getServerFacade().getTasks(request, TaskService.GET_TASK_URL);
            System.out.println("Response: " + response);
            System.out.println("Tasks from Response: " + response.getTasks());

            if (response.isSuccess()) {
//                this.statuses = pageOfItems.getFirst();
//                this.hasMorePages = pageOfItems.getSecond();
                this.tasks = response.getTasks();


                Tasks tasksInstance = Tasks.Companion.getInstance(); // Accessing Kotlin companion object
                for (Task newTask : tasks) {
                    System.out.println("adding task into Tasks in Kotlin: " + newTask.description);
                    List<Task> existingTasks = tasksInstance.getTasks();
                    boolean taskExists = false;
                    for (Task task : existingTasks) {
                        System.out.println("Task in existCheck: " + task);
                        System.out.println("newTask in existCheck: " + newTask);
                        if (task.taskName.equals(newTask.taskName) && task.description.equals(newTask.description)
                                && task.completed.equals(newTask.completed)) { // Change getTaskId() to the appropriate method for getting the task identifier
                            taskExists = true;
                            break;
                        }
                    }

                    // If the task doesn't exist, add it to the list of existing tasks
                    if (!taskExists) {
//                        existingTasks.add(newTask);
                        tasksInstance.addTask(newTask);
                    }
                }
                this.hasMorePages = response.getHasMorePages();
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get statuses", ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(TASKS_KEY, (Serializable) tasks);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }

    @Override
    protected Pair<List<Task>, Boolean> getItems() {
//        return getFakeData().getPageOfStatus(getLastItem(), getLimit());
        Pair<List<Task>, Boolean> Pair = null;
        Pair.setFirst(tasks);
        Pair.setSecond(false);
        return Pair;
    }

    // Add a method to return tasks
    public List<Task> getTasksTest() {
        return tasks;
    }
}
