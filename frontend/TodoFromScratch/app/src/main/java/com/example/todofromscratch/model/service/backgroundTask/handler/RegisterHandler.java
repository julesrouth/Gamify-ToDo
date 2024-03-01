package com.example.todofromscratch.model.service.backgroundTask.handler;


import android.os.Message;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.service.backgroundTask.BackgroundTask;
import com.example.todofromscratch.model.service.backgroundTask.RegisterTask;
import com.example.todofromscratch.model.service.backgroundTask.observer.RegisterObserver;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.User;

public class RegisterHandler extends MainHandler {

    private RegisterObserver observer;
    private RegisterTask registerTask;

    public RegisterHandler(RegisterObserver observer) {
        super();
        this.observer = observer;
    }

    @Override
    protected BackgroundTask getTaskType() {
        return registerTask;
    }

    @Override
    protected void success(Message msg) {
        User registeredUser = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);

        Cache.getInstance().setCurrUser(registeredUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        observer.hideErrorMessage();
        observer.showInfoMessage("Hello " + Cache.getInstance().getCurrUser().getName());
        try {
            observer.authenticationSucceeded(authToken, registeredUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void error(String message) {
        observer.authenticationFailed(message);
    }

    @Override
    protected void exception(Exception ex) {
        observer.authenticationFailed("Failed to register because of exception: " + ex);
    }

    @Override
    protected void updateFollow() {

    }
}