package com.example.todofromscratch.model.service.backgroundTask.observer;

import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.User;

public interface AuthenticateObserver {

    void authenticationSucceeded(AuthToken authToken, User user);

    void authenticationFailed(String message);

}
