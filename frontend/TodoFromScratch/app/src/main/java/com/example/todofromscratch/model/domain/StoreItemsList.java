package com.example.todofromscratch.model.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class StoreItemsList implements Serializable {

    private ArrayList<StoreItem> items;


    public StoreItemsList(ArrayList<StoreItem> items) {
        this.items = items;
    }

    public ArrayList<StoreItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<StoreItem> items) {
        this.items = items;
    }
}
