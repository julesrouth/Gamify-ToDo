package com.example.todofromscratch.cache;


import com.example.todofromscratch.model.domain.authtoken;
import com.example.todofromscratch.model.domain.User;

/**
 * The Cache class stores globally accessible data.
 */
public class Cache {
    private static Cache instance = new Cache();

    public static Cache getInstance() {
        return instance;
    }

    /**
     * The currently logged-in user.
     */
    private User currUser;
    /**
     * The auth token for the current user session.
     */
    private authtoken currUserAuthtoken;

    private Cache() {
        initialize();
    }

    private void initialize() {
        currUser = new User(null, null, null, null, null);
        currUserAuthtoken = null;
    }

    public void clearCache() {
        initialize();
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public authtoken getCurrUserAuthToken() {
        return currUserAuthtoken;
    }

    public void setCurrUserAuthToken(authtoken currUserAuthtoken) {
        this.currUserAuthtoken = currUserAuthtoken;
    }

    public static void setInstance(Cache instance) {
        Cache.instance = instance;
    }
}
