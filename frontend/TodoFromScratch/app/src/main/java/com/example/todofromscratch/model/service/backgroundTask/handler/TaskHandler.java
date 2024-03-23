package com.example.todofromscratch.model.service.backgroundTask.handler;

import android.os.Message;

import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.service.TaskService;
import com.example.todofromscratch.model.service.backgroundTask.TaskTask;

import java.util.List;

public class TaskHandler extends FeedHandler {

    private TaskService.GetTaskServiceObserver observer;

    public TaskHandler(TaskService.GetTaskServiceObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void success(Message msg) {
        List<Task> tasks = (List<Task>) msg.getData().getSerializable(TaskTask.TASKS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(TaskTask.MORE_PAGES_KEY);
        observer.addMoreItems(tasks, hasMorePages);
    }
}
