package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.PlayerItem;
import com.example.todofromscratch.model.domain.StoreItem;
import com.example.todofromscratch.model.domain.StoreItemsList;
import com.example.todofromscratch.model.net.ServerFacade;
import com.example.todofromscratch.model.net.request.ListPlayerItemsRequest;
import com.example.todofromscratch.model.net.request.ListStoreItemsRequest;
import com.example.todofromscratch.model.net.response.ListPlayerItemsResponse;
import com.example.todofromscratch.model.net.response.ListStoreItemsResponse;
import com.example.todofromscratch.model.service.StoreService;

import java.util.ArrayList;

public class ListPlayerItemsTask extends BackgroundTask {

    private static final String LIST_ITEMS_TAG = "ListPlayerItemsTask";
    public static final String PLAYER_ITEMS_KEY = "PlayerItems";

    private ServerFacade serverFacade;
    private ArrayList<PlayerItem> playerItems;

    public ListPlayerItemsTask(Handler messageHandler) {
        super(messageHandler);
    }

    @Override
    protected void runTask() {
        System.out.println("In runTask() in ListPlayerItemsTask");
        try {
            ListPlayerItemsRequest request = new ListPlayerItemsRequest(Cache.getInstance().getCurrUserAuthToken());
            ListPlayerItemsResponse response = getServerFacade().listPlayerItems(request, StoreService.LIST_PLAYER_ITEMS_URL);
            if (response.isSuccess()) {
                this.playerItems = response.getPlayerItems();
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LIST_ITEMS_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(PLAYER_ITEMS_KEY, playerItems);
    }

    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }


}

