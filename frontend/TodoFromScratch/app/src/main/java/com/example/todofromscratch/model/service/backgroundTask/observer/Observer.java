package com.example.todofromscratch.model.service.backgroundTask.observer;

public interface Observer {

    void displayException(Exception ex);

    void displayError(String message);

}
