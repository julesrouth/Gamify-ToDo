package com.example.todofromscratch.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

//import java.util.Observer;

import com.example.todofromscratch.model.service.backgroundTask.BackgroundTask;

public abstract class MainHandler extends Handler {

    private BackgroundTask taskType;

    public MainHandler() {
        super(Looper.getMainLooper());
        taskType = getTaskType();
    }

    @Override
    public void handleMessage(@NonNull Message msg) {

        boolean success = msg.getData().getBoolean(taskType.SUCCESS_KEY);
        if (success) {
            success(msg);
        } else if (msg.getData().containsKey(taskType.MESSAGE_KEY)) {
            String message = msg.getData().getString(taskType.MESSAGE_KEY);
            error(message);
        } else if (msg.getData().containsKey(taskType.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(taskType.EXCEPTION_KEY);
            exception(ex);
        }
        updateFollow();
    }

    protected abstract void updateFollow();

    protected abstract BackgroundTask getTaskType();

    protected abstract void success(Message msg);

    protected abstract void error(String message);

    protected abstract void exception(Exception ex);
}
