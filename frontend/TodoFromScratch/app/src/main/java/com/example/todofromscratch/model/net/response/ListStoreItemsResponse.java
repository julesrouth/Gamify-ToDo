package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.StoreItem;
import com.example.todofromscratch.model.domain.StoreItemsList;
import com.example.todofromscratch.model.domain.User;

import java.util.ArrayList;
import java.util.List;

public class ListStoreItemsResponse extends Response {

    private List<StoreItem> items = new ArrayList<>();


    public ListStoreItemsResponse(ArrayList<StoreItem> items, String message) {
        super(true, null);
        this.items = items;
    }

    public ListStoreItemsResponse(String message) {
        super(false, message);
    }

    public StoreItemsList getStoreItems() {
        return new StoreItemsList((ArrayList<StoreItem>) items);
    }

    public void setStoreItems(StoreItemsList storeItems) {
        this.items = storeItems.getItems();
    }
}
