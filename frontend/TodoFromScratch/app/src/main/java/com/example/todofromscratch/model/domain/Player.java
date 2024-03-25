package com.example.todofromscratch.model.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Comparable<Player>, Serializable {

    private String userId;
    private String characterName;
    private int level;
    private int experience;
    private int gold;

    private Stat stat;

    private ArrayList<PlayerItem> playerItems;

    public Player(String userId, String characterName, int level, int experience, int gold) {
        this.userId = userId;
        this.characterName = characterName;
        this.level = level;
        this.experience = experience;
        this.gold = gold;
        this.playerItems = new ArrayList<>();
        this.playerItems.add(new PlayerItem("Item 1"));
        this.playerItems.add(new PlayerItem("Item 2"));
        this.stat = new Stat();
    }

    public Stat getStat() {
        return stat;
    }
    public void setStat(Stat stat){
        this.stat = stat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public ArrayList<PlayerItem> getPlayerItems() {
        return playerItems;
    }

    public void setPlayerItems(ArrayList<PlayerItem> playerItems) {
        this.playerItems = playerItems;
    }
    public void addPlayerItem(PlayerItem item){
        this.playerItems.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return userId.equals(player.userId);
    }

    @Override
    public int compareTo(Player player) {
        return this.getUserId().compareTo(player.getUserId());
    }



}
