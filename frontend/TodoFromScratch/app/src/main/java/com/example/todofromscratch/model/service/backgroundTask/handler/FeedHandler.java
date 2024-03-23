package com.example.todofromscratch.model.service.backgroundTask.handler;

import android.os.Message;

import com.example.todofromscratch.model.service.TaskService;
import com.example.todofromscratch.model.service.backgroundTask.BackgroundTask;
import com.example.todofromscratch.model.service.backgroundTask.TaskTask;

import java.util.List;

public abstract class FeedHandler extends MainHandler {

    private TaskService.GetTaskServiceObserver observer;
    private TaskTask taskTask;

    public FeedHandler(TaskService.GetTaskServiceObserver observer) {
        super();
        this.observer = observer;
    }

    @Override
    protected void updateFollow() {
    }

    @Override
    protected BackgroundTask getTaskType() {
        return taskTask;
    }

//    @Override
//    protected void success(Message msg) {
//        List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetFeedTask.ITEMS_KEY);
//        boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
//
//        observer.addMoreItems(statuses, hasMorePages);
//    }

    @Override
    protected void error(String message) {
        String errorMessage = "Failed to get feed: ";
        observer.displayError(errorMessage + message);
    }

    @Override
    protected void exception(Exception ex) {
        observer.displayException(ex);
    }

}
