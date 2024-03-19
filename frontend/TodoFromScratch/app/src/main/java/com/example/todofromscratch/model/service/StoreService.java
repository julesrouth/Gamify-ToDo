package com.example.todofromscratch.model.service;

import com.example.todofromscratch.model.service.backgroundTask.AddItemTask;
import com.example.todofromscratch.model.service.backgroundTask.BackgroundTaskUtils;
import com.example.todofromscratch.model.service.backgroundTask.ListPlayerItemsTask;
import com.example.todofromscratch.model.service.backgroundTask.ListStoreItemsTask;
import com.example.todofromscratch.model.service.backgroundTask.handler.AddItemHandler;
import com.example.todofromscratch.model.service.backgroundTask.handler.ListPlayerItemsHandler;
import com.example.todofromscratch.model.service.backgroundTask.handler.ListStoreItemsHandler;
import com.example.todofromscratch.model.service.backgroundTask.observer.GeneralObserver;

public class StoreService {

    public static final String LIST_STORE_ITEMS_URL = "/listStoreItems";
    public static final String ADD_STORE_ITEMS_URL = "/addPlayerItem";

    public static final String LIST_PLAYER_ITEMS_URL = "/listPlayerItems";

    public void getStoreItems(GeneralObserver observer) {
        ListStoreItemsTask task = new ListStoreItemsTask(new ListStoreItemsHandler(observer));
        BackgroundTaskUtils.runTask(task);
    }

    public void addItem(String itemName, GeneralObserver observer) {
        AddItemTask task = new AddItemTask(itemName, new AddItemHandler(observer));
        BackgroundTaskUtils.runTask(task);
    }

    public void listPlayerItems(GeneralObserver observer) {
        ListPlayerItemsTask task = new ListPlayerItemsTask(new ListPlayerItemsHandler(observer));
        BackgroundTaskUtils.runTask(task);
    }
}
