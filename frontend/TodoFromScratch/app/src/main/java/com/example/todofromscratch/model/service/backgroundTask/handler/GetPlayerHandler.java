package com.example.todofromscratch.model.service.backgroundTask.handler;

import android.os.Message;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.Player;
import com.example.todofromscratch.model.domain.PlayerItem;
import com.example.todofromscratch.model.net.request.GetPlayerRequest;
import com.example.todofromscratch.model.service.backgroundTask.AddTask;
import com.example.todofromscratch.model.service.backgroundTask.BackgroundTask;
import com.example.todofromscratch.model.service.backgroundTask.GetPlayerTask;
import com.example.todofromscratch.model.service.backgroundTask.ListPlayerItemsTask;
import com.example.todofromscratch.model.service.backgroundTask.observer.GeneralObserver;

import java.util.ArrayList;

public class GetPlayerHandler extends MainHandler {

    private GeneralObserver observer;
    private GetPlayerTask task;
    public GetPlayerHandler(GeneralObserver observer) {
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
        Player player = (Player) msg.getData().getSerializable(GetPlayerTask.PLAYER_KEY);
        Cache.getInstance().setCurrPlayer(player);
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

