package com.example.todofromscratch.model.service.backgroundTask.observer;

import com.example.todofromscratch.model.domain.User;

public interface TaskObserver extends Observer {
    void successPost(String message);
}
