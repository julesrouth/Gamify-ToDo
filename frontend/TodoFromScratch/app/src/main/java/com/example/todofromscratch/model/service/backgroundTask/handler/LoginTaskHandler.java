package com.example.todofromscratch.model.service.backgroundTask.handler;

import android.os.Message;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.service.backgroundTask.BackgroundTask;
import com.example.todofromscratch.model.service.backgroundTask.LoginTask;
import com.example.todofromscratch.model.service.backgroundTask.observer.AuthenticateObserver;
import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.User;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class LoginTaskHandler extends MainHandler {

    private AuthenticateObserver observer;
    private LoginTask loginTask;

    public LoginTaskHandler(AuthenticateObserver observer) {
        super();
        this.observer = observer;
    }

    @Override
    protected void updateFollow() {
    }

    @Override
    protected BackgroundTask getTaskType() {
        return loginTask;
    }

    @Override
    protected void success(Message msg) {
        User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
        authtoken authtoken = (authtoken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);

        //Cache user session info
        Cache.getInstance().setCurrUser(loggedInUser);
        Cache.getInstance().setCurrUserAuthToken(authtoken);
        System.out.println("Current user in login User: " + loggedInUser.getFirstName());
        System.out.println("Current authtoken in lgoin user: " + authtoken.token);

        observer.authenticationSucceeded(authtoken, loggedInUser);
    }

    @Override
    protected void error(String message) {
        observer.authenticationFailed(message);
    }

    @Override
    protected void exception(Exception ex) {
        observer.authenticationFailed("Failed to login because of exception: " + ex.getMessage());
    }

//    private final UserService.LoginObserver observer;
//
//    public LoginTaskHandler(UserService.LoginObserver observer) {
//        super(Looper.getMainLooper());
//        this.observer = observer;
//    }
//
//    @Override
//    public void handleMessage(Message message) {
//        Bundle bundle = message.getData();
//        boolean success = bundle.getBoolean(LoginTask.SUCCESS_KEY);
//        if (success) {
//            User user = (User) bundle.getSerializable(LoginTask.USER_KEY);
//            AuthToken authToken = (AuthToken) bundle.getSerializable(LoginTask.AUTH_TOKEN_KEY);
//            observer.handleSuccess(user, authToken);
//        } else if (bundle.containsKey(LoginTask.MESSAGE_KEY)) {
//            String errorMessage = bundle.getString(LoginTask.MESSAGE_KEY);
//            observer.handleFailure(errorMessage);
//        } else if (bundle.containsKey(LoginTask.EXCEPTION_KEY)) {
//            Exception ex = (Exception) bundle.getSerializable(LoginTask.EXCEPTION_KEY);
//            observer.handleException(ex);
//        }
//    }
}
