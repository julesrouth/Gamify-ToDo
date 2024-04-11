package com.example.todofromscratch.cache;


import com.example.todofromscratch.game.Game;
import com.example.todofromscratch.model.domain.PlayerItem;
import com.example.todofromscratch.model.domain.Player;
import com.example.todofromscratch.model.domain.StoreItemsList;
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

    private Game game;
    /**
     * The auth token for the current user session.
     */
    private static AuthToken currUserAuthtoken;

    private Player currPlayer;
    private StoreItemsList storeItems = new StoreItemsList(null);

    private ArrayList<PlayerItem> playerItems = new ArrayList<>();

    private Cache() {
        initialize();
    }

    private void initialize() {
        currUser = new User(null, null, null, null, null);
        currPlayer = new Player("test", "testName", 0, 0, 0);
        game = new Game();
        currUserAuthtoken = null;
        currPlayer = null;
        storeItems = new StoreItemsList(null);
        playerItems.clear();
    }

    public Game getGame(){
        return game;
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

    public String getCurrUserID(){ return currUserAuthtoken.userId; }


    // 0 == playing, 1 == won, 2 == lost    
    public int getGameState(){
        return game.getGameState();
    }

    public AuthToken getCurrUserAuthToken() {
        return currUserAuthtoken;
    }

    public void setCurrUserAuthToken(AuthToken currUserAuthtoken) {
        this.currUserAuthtoken = currUserAuthtoken;
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
