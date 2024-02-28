package com.example.todofromscratch.presenter;

import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.service.UserService;
import com.example.todofromscratch.model.service.backgroundTask.observer.AuthenticateObserver;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.net.request.LoginRequest;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter extends AuthenticatePresenter implements AuthenticateObserver {

    private static final String LOG_TAG = "LoginPresenter";

    private View view;

    public LoginPresenter(View view) {
        super(view);
        this.view = view;
    }

    public void login(String username, String password) {
        if (validateLogin(username, password)) {
            view.hideErrorMessage();
            view.showInfoMessage("Logging in...");

            UserService userService = new UserService();
            userService.login(username, password, this);
        }
    }

    public boolean validateLogin(String username, String password) {
//        if (username.length() > 0 && username.charAt(0) != '@') {
//            view.showErrorMessage("username must begin with @.");
//            return false;
//        }
        if (username.length() < 1) {
            view.showErrorMessage("username must contain 1 or more characters");
            return false;
        }
        if (password.length() == 0) {
            view.showErrorMessage("Password cannot be empty.");
            return false;
        }
        return true;
    }

//    private final View view;
//
//    /**
//     * The interface by which this presenter communicates with it's view.
//     */
//    public interface View {
//        void loginSuccessful(User user, AuthToken authToken);
//        void loginUnsuccessful(String message);
//    }
//
//    /**
//     * Creates an instance.
//     *
//     * @param view the view for which this class is the presenter.
//     */
//    public LoginPresenter(View view) {
//        // An assertion would be better, but Android doesn't support Java assertions
//        if(view == null) {
//            throw new NullPointerException();
//        }
//        this.view = view;
//    }
//
//    /**
//     * Initiates the login process.
//     *
//     * @param username the user's username.
//     * @param password the user's password.
//     */
//    public void initiateLogin(String username, String password) {
//        UserService userService = new UserService();
//        userService.login(username, password, this);
//    }
//
//    /**
//     * Invoked when the login request completes if the login was successful. Notifies the view of
//     * the successful login.
//     *
//     * @param user the logged-in user.
//     * @param authToken the session auth token.
//     */
//    @Override
//    public void handleSuccess(User user, AuthToken authToken) {
//        // Cache user session information
//        Cache.getInstance().setCurrUser(user);
//        Cache.getInstance().setCurrUserAuthToken(authToken);
//
//        view.loginSuccessful(user, authToken);
//    }
//
//    /**
//     * Invoked when the login request completes if the login request was unsuccessful. Notifies the
//     * view of the unsuccessful login.
//     *
//     * @param message error message.
//     */
//    @Override
//    public void handleFailure(String message) {
//        String errorMessage = "Failed to login: " + message;
//        Log.e(LOG_TAG, errorMessage);
//        view.loginUnsuccessful(errorMessage);
//    }
//
//    /**
//     * A callback indicating that an exception occurred in an asynchronous method this class is
//     * observing.
//     *
//     * @param exception the exception.
//     */
//    @Override
//    public void handleException(Exception exception) {
//        String errorMessage = "Failed to login because of exception: " + exception.getMessage();
//        Log.e(LOG_TAG, errorMessage, exception);
//        view.loginUnsuccessful(errorMessage);
//    }
}