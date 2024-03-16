package com.example.todofromscratch.model.domain;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {

    private String userId;
    private String characterName;
    private int level;
    private int experience;
    private int gold;

    public Player(String userId, String characterName, int level, int experience, int gold) {
        this.userId = userId;
        this.characterName = characterName;
        this.level = level;
        this.experience = experience;
        this.gold = gold;
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
