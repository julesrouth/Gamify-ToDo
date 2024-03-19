package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.todofromscratch.model.net.ServerFacade;
import com.example.todofromscratch.model.service.UserService;
import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.net.request.LoginRequest;
import com.example.todofromscratch.model.net.response.LoginResponse;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends BackgroundTask {

    private static final String LOG_TAG = "LoginTask";

    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";

    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    private String username;
    /**
     * The user's password.
     */
    private String password;

    /**
     * The logged-in user returned by the server.
     */
    protected User user;

    /**
     * The auth token returned by the server.
     */
    protected authtoken authtoken;

    private ServerFacade serverFacade;

    public LoginTask(UserService userService, String username, String password, Handler messageHandler) {
        super(messageHandler);

        this.username = username;
        this.password = password;
    }

    @Override
    protected void runTask() {
        System.out.println("In runTask() in LoginTask()");
        try {
            LoginRequest request = new LoginRequest(username, password);
            LoginResponse response = getServerFacade().login(request, UserService.URL_PATH);
            System.out.println("Got response");
            if (response.isSuccess()) {
                System.out.println("It was a success");
                this.user = response.getUser();
                this.authtoken = response.getAuthtoken();

                sendSuccessMessage();
            } else {
                System.out.println("It was not a success");
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, this.user);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, this.authtoken);
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their instance from this method to
     * allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }
}

