package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Handler;

import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.net.ServerFacade;


public abstract class AuthenticatedTask extends BackgroundTask {

    /**
     * Auth token for logged-in user.
     * This user is the "follower" in the relationship.
     */
    private final authtoken authtoken;

    private ServerFacade serverFacade;

    protected AuthenticatedTask(authtoken authtoken, Handler messageHandler) {
        super(messageHandler);
        this.authtoken = authtoken;
    }

    public authtoken getAuthtoken() {
        return authtoken;
    }

    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }
}