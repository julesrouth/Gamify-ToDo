package com.example.todofromscratch.model.service.backgroundTask;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import com.example.todofromscratch.model.service.UserService;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.net.request.LoginRequest;
import com.example.todofromscratch.model.net.request.RegisterRequest;
import com.example.todofromscratch.model.net.response.LoginResponse;
import com.example.todofromscratch.model.net.response.RegisterResponse;
import com.example.todofromscratch.util.Pair;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask {

    private static final String LOG_TAG = "RegisterTask";

    /**
     * The user's first name.
     */
    private final String firstName;

    /**
     * The user's last name.
     */
    private final String lastName;
    private final String email;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
//    private final String image;
    private User registeredUser;
    private AuthToken authToken;

    public RegisterTask(String username, String password, String email, String firstName, String lastName,
                        Handler messageHandler) {
        super(messageHandler, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
//        this.image = image;
    }

    @Override
    protected void runTask()  throws IOException {
//        Pair<User, AuthToken> loginResult = runAuthenticationTask();
//
//        authenticatedUser = loginResult.getFirst();
//        authToken = loginResult.getSecond();

        try {
            RegisterRequest request = new RegisterRequest(username, password, email, firstName, lastName);
            RegisterResponse response = getServerFacade().register(request, UserService.REGISTER_URL_PATH);

            if (response.isSuccess()) {
                this.registeredUser = response.getUser();
                this.authToken = response.getAuthToken();
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected Pair<User, AuthToken> runAuthenticationTask() {
        return new Pair<>(registeredUser, authToken);
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, registeredUser);
        msgBundle.putSerializable(AUTH_TOKEN_KEY, authToken);
    }
}
