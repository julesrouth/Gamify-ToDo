package com.example.todofromscratch.game;


import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Item> items;

    public Shop() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public String printItems() {
        StringBuilder tempStr = new StringBuilder();
        for (Item item : this.items) {
            tempStr.append(item.toString()).append("\n");
        }
        return tempStr.toString();
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void printShop() {
        Global.clearScreen();
        StringBuilder tempStr = new StringBuilder();
        tempStr.append('-').append("Global.LINELENGTH").append("\n");
        tempStr.append("|  Shop").append(" ".repeat(Global.LINELENGTH - 8)).append("|\n");
        tempStr.append('-').append("Global.LINELENGTH").append("\n");
        for (Item item : this.items) {
            int intLen = String.valueOf(item.getPrice()).length();
            tempStr.append("|  ").append(item.toString());
            tempStr.append(" ".repeat(Global.LINELENGTH - item.toString().length() - 7 - intLen))
                   .append(item.getPrice()).append("   ".repeat(3)).append("|\n");
        }
        tempStr.append('-').append("Global.LINELENGTH").append("\n");
        System.out.println(tempStr.toString());
    }

    public static void addItemsShopTest(Shop shop) {
        shop.addItem(new ShopItem("Sword", new ItemStat(10, 0, 0), 100));
        shop.addItem(new ShopItem("Shield", new ItemStat(0, 10, 0), 100));
        shop.addItem(new ShopItem("Potion", new ItemStat(0, 0, 10), 100));
    }

    public static void main(String[] args) {
        System.out.println("Testing Shop");
        Shop shop = new Shop();
        addItemsShopTest(shop);
        System.out.println(shop.printItems());
        System.out.println(shop.getItems());
        shop.removeItem(shop.getItems().get(0));
        System.out.println(shop.getItems());
    }
}




