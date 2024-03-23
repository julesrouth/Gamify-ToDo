package com.example.todofromscratch.model.domain;

public class PlayerItem {
    private String itemName;

    public PlayerItem(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }
}
