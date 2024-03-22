package com.example.todofromscratch.presenter;

import android.util.Log;

import com.example.todofromscratch.model.service.UserService;
import com.example.todofromscratch.model.service.backgroundTask.observer.GeneralObserver;

public class PlayerPresenter implements GeneralObserver {

    public interface View{
        void showInfoMessage(String message);
        void taskSuccess(String message);
        void taskFail(String message);
    }

    private final PlayerPresenter.View view;

    public PlayerPresenter(PlayerPresenter.View view) {
        this.view = view;
    }

    @Override
    public void success(String message) {
        view.taskSuccess(message);
    }

    @Override
    public void fail(String message) {
        view.taskFail("Failed: " + message);
    }

    public void getPlayer() {
        Log.i("Player_Presenter", "Getting player");
        UserService userService = new UserService();
        userService.getPlayer(this);
    }
}