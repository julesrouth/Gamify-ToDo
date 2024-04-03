package com.example.todofromscratch.presenter;

import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.service.backgroundTask.observer.AuthenticateObserver;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.User;

public class AuthenticatePresenter implements AuthenticateObserver {

    public interface View{

        void showErrorMessage(String message);

        void hideErrorMessage();

        void hideInfoMessage();

        void showInfoMessage(String message);

        void openMainView(User user);

    }

    private View view;

    public AuthenticatePresenter(View view) {
        this.view = view;
    }

    @Override
    public void authenticationSucceeded(AuthToken authtoken, User user) {
//        Log.i("AuthPresenter", "in authenticationSucceeded");
        System.out.println("In authenticationSucceeded");
        view.hideErrorMessage();
        view.hideInfoMessage();
        System.out.println(user.getName());
        Cache.getInstance().setCurrUser(user);
        Cache.getInstance().setCurrUserAuthToken(authtoken);
        view.showInfoMessage("Hello, " + user.getName());
        view.openMainView(user);
    }

    @Override
    public void authenticationFailed(String message) {
        view.showErrorMessage(message);
    }
}
