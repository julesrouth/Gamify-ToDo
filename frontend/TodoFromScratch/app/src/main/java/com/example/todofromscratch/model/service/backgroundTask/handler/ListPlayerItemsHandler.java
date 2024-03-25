package com.example.todofromscratch.model.service.backgroundTask.handler;

import android.os.Message;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.PlayerItem;
import com.example.todofromscratch.model.domain.StoreItem;
import com.example.todofromscratch.model.domain.StoreItemsList;
import com.example.todofromscratch.model.service.backgroundTask.BackgroundTask;
import com.example.todofromscratch.model.service.backgroundTask.ListPlayerItemsTask;
import com.example.todofromscratch.model.service.backgroundTask.observer.GeneralObserver;

import java.util.ArrayList;

public class ListPlayerItemsHandler  extends MainHandler {

    private GeneralObserver observer;
    private ListPlayerItemsTask task;

    public ListPlayerItemsHandler(GeneralObserver observer) {
        super();
        this.observer = observer;
    }

    @Override
    protected void updateFollow() {

    }

    @Override
    protected BackgroundTask getTaskType() {
        return task;
    }

    @Override
    protected void success(Message msg) {
        ArrayList<PlayerItem> playerItems = (ArrayList<PlayerItem>) msg.getData().getSerializable(ListPlayerItemsTask.PLAYER_ITEMS_KEY);
        Cache.getInstance().setPlayerItems(playerItems);
        try {
            observer.success("Success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    protected void error(String message) {
        observer.fail(message);
    }

    @Override
    protected void exception(Exception ex) {
        observer.fail(ex.getMessage());
    }
}
