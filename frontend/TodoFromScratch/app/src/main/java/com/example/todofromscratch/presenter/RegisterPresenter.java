package com.example.todofromscratch.presenter;

import android.widget.EditText;

import com.example.todofromscratch.model.service.UserService;
import com.example.todofromscratch.model.service.backgroundTask.observer.RegisterObserver;

public class RegisterPresenter extends AuthenticatePresenter implements RegisterObserver {

    private View view;
    private UserService userService;

    public RegisterPresenter(RegisterPresenter.View view) {
        super(view);
        this.view = view;
        userService = new UserService();
    }

    @Override
    public void hideErrorMessage() {
        view.hideErrorMessage();
    }

    @Override
    public void showInfoMessage(String message) {
        view.showInfoMessage(message);
    }

    public boolean validateRegistration(String username, String password, String email, String firstName, String lastName) {
        if (firstName.length() == 0) {
            view.showErrorMessage("First Name cannot be empty.");
            return false;
        }
        if (lastName.length() == 0) {
            view.showErrorMessage("Last Name cannot be empty.");
            return false;
        }
        if (username.length() == 0) {
            view.showErrorMessage("Alias cannot be empty.");
            return false;
        }
        if (password.length() == 0) {
            view.showErrorMessage("Password cannot be empty.");
            return false;
        }
        if (email.length() == 0) {
            view.showErrorMessage("email cannot be empty.");
        }
        return true;
    }

    public void registerUser(String username, String password, String email, String firstname, String lastname) {
        // Send register request.
        if (validateRegistration(username, password, email, firstname, lastname)) {
            view.hideErrorMessage();
            view.showInfoMessage("Registering...");

            UserService userService = new UserService();
            userService.register(username, password, email, firstname, lastname, this);
        }
    }
}