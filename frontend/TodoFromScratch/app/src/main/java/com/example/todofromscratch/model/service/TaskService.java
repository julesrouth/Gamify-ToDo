package com.example.todofromscratch.model.service;

import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.service.backgroundTask.AddTask;
import com.example.todofromscratch.model.service.backgroundTask.TaskTask;
import com.example.todofromscratch.model.service.backgroundTask.handler.TaskHandler;
import com.example.todofromscratch.model.service.backgroundTask.observer.PagedTaskObserver;

import java.util.List;

public class TaskService extends Service {


    public static final String GET_TASK_URL = "/listTasksForUser";
    public static final String GET_ADDTASK_URL = "/createTask";

    public interface GetTaskServiceObserver extends PagedTaskObserver<Task> {
        //        void handleSuccess(List<Status> statuses, AuthToken authToken);
//        void handleFailure(String message);
//        void handleException(Exception exception);
        void addMoreItems(List<Task> items, boolean hasMorePages);
        void successPost(String message);
        void displayException(Exception ex);
        void displayError(String message);
    }

    public TaskService() {}

    public void loadMoreItems(authtoken currUserAuthtoken, User user, int pageSize, Task lastStatus, GetTaskServiceObserver observer) {
        TaskTask getTaskTask = new TaskTask(currUserAuthtoken,
                user, pageSize, lastStatus, new TaskHandler(observer));
        execute(getTaskTask);
    }

    public void addTask(authtoken currUserAuthToken, Task newTask, GetTaskServiceObserver observer) {
        AddTask getAddTask = new AddTask(currUserAuthToken,
                newTask, new TaskHandler(observer));
        execute(getAddTask);
    }
//
//
//    public GetStoryTask getStoryTask(AuthToken authToken, User targetUser, int limit, Status lastStatus, GetStatusServiceObserver observer) {
//        return new GetStoryTask(authToken, targetUser, limit, lastStatus, new GetStoryHandler(observer));
//    }

}
