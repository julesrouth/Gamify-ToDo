package com.example.todofromscratch.model.service.backgroundTask.observer;

public interface RegisterObserver extends AuthenticateObserver{

    void hideErrorMessage();

    void showInfoMessage(String message);
}
