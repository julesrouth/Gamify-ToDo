package com.example.todofromscratch.game;


public class ShopItem extends Item {
    private int price;

    public ShopItem(String name, ItemStat stats, int price, String attributes) {
        super(name, stats, attributes);
        this.price = price;
    }
    public ShopItem(String name, ItemStat stats, int price) {
        this(name, stats, price, "");
    }
    public int getShopItemPrice() {
        return price;
    }   
}
