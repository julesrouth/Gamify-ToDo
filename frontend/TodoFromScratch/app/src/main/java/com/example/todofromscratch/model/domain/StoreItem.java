package com.example.todofromscratch.model.domain;

public class StoreItem {

    private String itemName;
    private String effects;
    private int cost;

    public StoreItem(String itemName, String effects, int cost) {
        this.itemName = itemName;
        this.effects = effects;
        this.cost = cost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}