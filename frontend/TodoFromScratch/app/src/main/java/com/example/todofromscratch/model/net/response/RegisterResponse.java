package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.User;

public class RegisterResponse extends Response {

    private User user;
    private authtoken authtoken;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public RegisterResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the now logged in user.
     * @param authToken the auth token representing this user's session with the server.
     */
    public RegisterResponse(User user, authtoken authtoken) {
        super(true, null);
        this.user = user;
        this.authtoken = authtoken;
    }

    /**
     * Returns the logged in user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the auth token.
     *
     * @return the auth token.
     */
    public authtoken getAuthtoken() {
        return authtoken;
    }
}

