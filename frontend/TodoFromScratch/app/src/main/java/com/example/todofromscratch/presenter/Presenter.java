package com.example.todofromscratch.presenter;

public abstract class Presenter {

    public interface View { //observes Presenter

        void displayMessage(String message);

    }

    private View view;

    public Presenter(View view) {
        this.view = view;
    }

}
