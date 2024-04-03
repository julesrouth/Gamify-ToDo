package com.example.todofromscratch.model.domain;


public class Stat {
    private int health;
    private int attack;
    private int defense;
    private int level;
    private int mana;
    private int speed;
    private int maxHealth;
    private int maxMana;

    public Stat() {
        this.health = 30;
        this.attack = 10;
        this.defense = 10;
        this.level = 5;
        this.mana = this.level * 3;
        this.speed = 10;
        this.maxHealth = this.health;
        this.maxMana = this.mana;
    }

    public Stat(int health, int attack, int defense, int speed, int level) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
        this.mana = level * 3;
        this.speed = speed;
        this.maxHealth = health;
        this.maxMana = this.mana;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getMaxMana() {
        return maxMana;
    }
    
    public int getHealth() {
        return health;
    }
    public int getAttack() {
        return attack;
    }
    public int getDefense() {
        return defense;
    }
    public int getLevel() {
        return level;
    }
    public int getMana() {
        return mana;
    }
    public int getSpeed() {
        return speed;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Health: " + health + " Attack: " + attack + " Defense: " + defense +
               " Level: " + level + " Mana: " + mana;
    }

    public static void main(String[] args) {
        System.out.println("Testing Stat");
        Stat stat = new Stat();
        System.out.println(stat.toString());
    }
}
