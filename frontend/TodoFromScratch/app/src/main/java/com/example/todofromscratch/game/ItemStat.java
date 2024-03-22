package com.example.todofromscratch.game;

public class ItemStat extends Stat {
    private int mana;
    private String features;

    public ItemStat(int health, int attack, int defense, int speed, int level, int mana, String features) {
        super(health, attack, defense, speed, level);
        this.mana = mana;
        this.features = features;
    }
    public ItemStat(int health, int attack, int defense){
        this(health, attack, defense, 0, 0, 0, "");
    }
}
