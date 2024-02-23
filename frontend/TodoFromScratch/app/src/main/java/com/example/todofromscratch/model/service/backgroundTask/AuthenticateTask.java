package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.util.Pair;

public abstract class AuthenticateTask extends BackgroundTask {

    public static final String USER_KEY = "user";
    public static final String AUTH_TOKEN_KEY = "auth-token";

    private User authenticatedUser;

    private AuthToken authToken;

    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    protected final String username;

    /**
     * The user's password.
     */
    protected final String password;

    protected AuthenticateTask(Handler messageHandler, String username, String password) {
        super(messageHandler);
        this.username = username;
        this.password = password;
    }


    @Override
    protected void runTask()  throws IOException {
        Pair<User, AuthToken> loginResult = runAuthenticationTask();

        authenticatedUser = loginResult.getFirst();
        authToken = loginResult.getSecond();

        // Call sendSuccessMessage if successful
        sendSuccessMessage();
        // or call sendFailedMessage if not successful
        // sendFailedMessage()
    }

    protected abstract Pair<User, AuthToken> runAuthenticationTask();

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, authenticatedUser);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
    }
}

