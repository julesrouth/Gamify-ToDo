package com.example.todofromscratch.cache;


import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.PlayerItem;
import com.example.todofromscratch.model.domain.Player;
import com.example.todofromscratch.model.domain.StoreItemsList;
import com.example.todofromscratch.model.domain.User;

import java.util.ArrayList;

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
    private AuthToken currUserAuthToken;

    private Player currPlayer;
    private StoreItemsList storeItems = new StoreItemsList(null);

    private ArrayList<PlayerItem> playerItems = new ArrayList<>();

    private Cache() {
        initialize();
    }

    private void initialize() {
        currUser = new User(null, null, null, null, null);
        currUserAuthToken = null;
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

    public AuthToken getCurrUserAuthToken() {
        return currUserAuthToken;
    }

    public void setCurrUserAuthToken(AuthToken currUserAuthToken) {
        this.currUserAuthToken = currUserAuthToken;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public StoreItemsList getStoreItems() {
        return storeItems;
    }

    public void setStoreItems(StoreItemsList storeItems) {
        this.storeItems = storeItems;
    }

    public ArrayList<PlayerItem> getPlayerItems() {
        return playerItems;
    }

    public void setPlayerItems(ArrayList<PlayerItem> items) {
        this.playerItems = items;
    }

    public static void setInstance(Cache instance) {
        Cache.instance = instance;
    }
}
