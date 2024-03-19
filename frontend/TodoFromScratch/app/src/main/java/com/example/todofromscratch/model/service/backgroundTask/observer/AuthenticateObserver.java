package com.example.todofromscratch.model.service.backgroundTask.observer;

import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.User;

public interface AuthenticateObserver {

    void authenticationSucceeded(authtoken authtoken, User user);

    void authenticationFailed(String message);

}
