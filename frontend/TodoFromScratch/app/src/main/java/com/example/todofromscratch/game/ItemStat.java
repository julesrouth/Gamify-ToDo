package com.example.todofromscratch.game;

import com.example.todofromscratch.model.domain.Stat;

public class ItemStat extends Stat {
    private int mana;
    private String features;

    public ItemStat(int health, int attack, int defense, int speed, int level, int mana, String features) {
        super(health, attack, defense, speed, level);
        this.mana = mana;
        this.features = features;
    }
    public ItemStat(int attack, int defense, int speed){
        this(0, attack, defense, speed, 0, 0, "");
    }
}
