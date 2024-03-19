package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Handler;

import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.net.ServerFacade;


public abstract class AuthenticatedTask extends BackgroundTask {

    /**
     * Auth token for logged-in user.
     * This user is the "follower" in the relationship.
     */
    private final AuthToken authtoken;

    private ServerFacade serverFacade;

    protected AuthenticatedTask(AuthToken authtoken, Handler messageHandler) {
        super(messageHandler);
        this.authtoken = authtoken;
    }

    public AuthToken getAuthtoken() {
        return authtoken;
    }

    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }
}