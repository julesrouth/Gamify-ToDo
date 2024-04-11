package com.example.todofromscratch.game;


import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<ShopItem> items;

    public Shop() {
        this.items = new ArrayList<>();
    }

    public void addItem(ShopItem item) {
        this.items.add(item);
    }
    public void removeItem(ShopItem item) {
        this.items.remove(item);
    }

    public String printItems() {
        StringBuilder tempStr = new StringBuilder();
        for (Item item : this.items) {
            tempStr.append(item.toString()).append("\n");
        }
        //print all the prices
        for (ShopItem item : this.items) {
            tempStr.append(item.toString()).append(" ").append(item.getShopItemPrice()).append("\n");
        }
        return tempStr.toString();
    }

    public List<ShopItem> getItems() {
        return this.items;
    }

    public void printShop() {
        Global.clearScreen();
        StringBuilder tempStr = new StringBuilder();
        tempStr.append('-').append("-".repeat(Global.LINELENGTH)).append("\n");
        tempStr.append("|  Shop").append(" ".repeat(Global.LINELENGTH - 8)).append("|\n");
        tempStr.append('-').append("-".repeat(Global.LINELENGTH)).append("\n");
        for (ShopItem item : this.items) {
            int intLen = String.valueOf(item.getShopItemPrice()).length();
            tempStr.append("|  ").append(item.toString());
            tempStr.append(" ".repeat(Global.LINELENGTH - item.toString().length() - 7 - intLen))
                   .append(item.getShopItemPrice()).append("   ".repeat(3)).append("|\n");
        }
        tempStr.append('-').append("").append("\n");
        System.out.println(tempStr.toString());
    }

    public static void addItemsShopTest(Shop shop) {
        // shop.addItem(new ShopItem("Sword", new ItemStat(10, 0, 0), 100));
        // shop.addItem(new ShopItem("Shield", new ItemStat(0, 10, 0), 100));
        // shop.addItem(new ShopItem("Potion", new ItemStat(0, 0, 10), 100));
        shop.addItem(new ShopItem("Wood Sword", new ItemStat(8, 0, 0), 100));
        shop.addItem(new ShopItem("Stone Sword", new ItemStat(12, 0, 0), 200));
        shop.addItem(new ShopItem("Steel Sword", new ItemStat(16, 0, 0), 300));
        shop.addItem(new ShopItem("Titanium Sword", new ItemStat(20, 0, 0), 400));
        shop.addItem(new ShopItem("Mithral Sword", new ItemStat(24, 0, 0), 500));
        shop.addItem(new ShopItem("Wood Armor", new ItemStat(0, 8, 0), 100));
        shop.addItem(new ShopItem("Stone Armor", new ItemStat(0, 12, 0), 200));
        shop.addItem(new ShopItem("Steel Armor", new ItemStat(0, 16, 0), 300));
        shop.addItem(new ShopItem("Titanium Armor", new ItemStat(0, 20, 0), 400));
        shop.addItem(new ShopItem("Mithral Armor", new ItemStat(0, 24, 0), 500));
        shop.addItem(new ShopItem("Wood Boots", new ItemStat(0, 0, 8), 100));
        shop.addItem(new ShopItem("Stone Boots", new ItemStat(0, 0, 12), 200));
        shop.addItem(new ShopItem("Steel Boots", new ItemStat(0, 0, 16), 300));
        shop.addItem(new ShopItem("Titanium Boots", new ItemStat(0, 0, 20), 400));
        shop.addItem(new ShopItem("Mithral Boots", new ItemStat(0, 0, 24), 500));
    

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




