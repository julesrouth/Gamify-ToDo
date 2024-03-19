package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.net.request.LoginRequest;

/**
 * A response for a {@link LoginRequest}.
 */
public class LoginResponse extends Response {

    private User user;
    private String username;
    private String token;
    private authtoken authtoken;

    public LoginResponse(String username, String token) {
        super(true, null);
        System.out.println("In login response constructor");
        this.username = username;
        this.token = token;
    }

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public LoginResponse(String message) {
        super(false, message);
        System.out.println("In login response constructor");
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the now logged in user.
     * @param authToken the auth token representing this user's session with the server.
     */
    public LoginResponse(User user, authtoken authtoken) {
        super(true, null);
        this.user = user;
        this.authtoken = authtoken;
        System.out.println("In login response constructor");
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
