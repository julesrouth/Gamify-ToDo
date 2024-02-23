package com.example.todofromscratch.model.service;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.todofromscratch.model.service.backgroundTask.BackgroundTask;

public class Service {

    protected void execute(BackgroundTask task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
    }

}
