package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.PlayerItem;
import com.example.todofromscratch.model.domain.StoreItem;

import java.util.ArrayList;

public class ListPlayerItemsResponse  extends Response {

    private ArrayList<PlayerItem> items = new ArrayList<>();

    public ListPlayerItemsResponse(ArrayList<PlayerItem> items, String message) {
        super(true, null);
        this.items = items;
    }

    public ListPlayerItemsResponse(String message) {
        super(false, message);
    }

    public ArrayList<PlayerItem> getPlayerItems() {
        return items;
    }
}

