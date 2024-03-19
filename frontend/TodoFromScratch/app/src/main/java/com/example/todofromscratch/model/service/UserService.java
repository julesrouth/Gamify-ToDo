package com.example.todofromscratch.model.service;

import com.example.todofromscratch.model.service.backgroundTask.BackgroundTaskUtils;
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import com.example.todofromscratch.model.service.backgroundTask.LoginTask;
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import com.example.todofromscratch.model.service.backgroundTask.RegisterTask;
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetUserHandler;
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetUserStatusHandler;
import com.example.todofromscratch.model.service.backgroundTask.handler.LoginTaskHandler;
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LogoutHandler;
import com.example.todofromscratch.model.service.backgroundTask.handler.RegisterHandler;
import com.example.todofromscratch.model.service.backgroundTask.observer.AuthenticateObserver;
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.LogoutObserver;
import com.example.todofromscratch.model.service.backgroundTask.observer.RegisterObserver;
//import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.StatusObserver;
import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.User;

/**
 * Contains the business logic to support the login operation.
 */
public class UserService extends Service {

    public static final String URL_PATH = "/login";
    public static final String LOGOUT_URL_PATH = "/logout";
    public static final String GETUSER_URL_PATH = "/getuser";
    public static final String REGISTER_URL_PATH = "/register";

    /**
     * An observer interface to be implemented by observers who want to be notified when
     * asynchronous operations complete.
     */
    public interface LoginObserver {
        void handleSuccess(User user, authtoken authtoken);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     */
    public UserService() {
    }

    /**
     * Makes an asynchronous login request.
     *
     * @param username the user's name.
     * @param password the user's password.
     */
    public void login(String username, String password, AuthenticateObserver observer) {
        LoginTask loginTask = getLoginTask(username, password, observer);
        BackgroundTaskUtils.runTask(loginTask);
    }

//    public void logout(AuthToken currUserAuthToken, LogoutObserver observer) {
//        LogoutTask logoutTask = new LogoutTask(currUserAuthToken, new LogoutHandler(observer));
//        BackgroundTaskUtils.runTask(logoutTask);
//    }

    public void register(String username, String password, String email, String firstName, String lastName,  RegisterObserver observer) {
        RegisterTask registerTask = getRegisterTask(username, password,
                email, firstName, lastName, observer);

        BackgroundTaskUtils.runTask(registerTask);
    }

//    public void getUser(AuthToken authToken, TextView userAlias, StatusObserver observer) {
//        GetUserTask getUserTask = new GetUserTask(authToken,
//                userAlias.getText().toString(), new GetUserHandler(observer));
//        BackgroundTaskUtils.runTask(getUserTask);
//    }

//    public void getUserStatus(AuthToken authToken, TextView userAlias, StatusObserver observer) {
//        GetUserTask getUserTask = new GetUserTask(authToken,
//                userAlias.getText().toString(), new GetUserStatusHandler(observer));
//        BackgroundTaskUtils.runTask(getUserTask);
//    }

//    public void getUserStatusClickable(AuthToken currUserAuthToken, String clickable, StatusObserver observer) {
//        GetUserTask getUserTask = new GetUserTask(currUserAuthToken,
//                clickable, new GetUserStatusHandler(observer));
//        BackgroundTaskUtils.runTask(getUserTask);
//    }

    /**
     * Returns an instance of {@link LoginTask}. Allows mocking of the LoginTask class for
     * testing purposes. All usages of LoginTask should get their instance from this method to
     * allow for proper mocking.
     *
     * @return the instance.
     */
    LoginTask getLoginTask(String username, String password, AuthenticateObserver observer) {
        return new LoginTask(this, username, password, new LoginTaskHandler(observer));
    }

    private RegisterTask getRegisterTask(String username, String password, String email, String firstName, String lastName, RegisterObserver observer) {
        return new RegisterTask(this, username, password, email, firstName, lastName, new RegisterHandler(observer));
    }
}

