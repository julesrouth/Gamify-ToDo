package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.net.ServerFacade;
import com.example.todofromscratch.model.net.request.AddItemRequest;
import com.example.todofromscratch.model.net.response.AddItemResponse;
import com.example.todofromscratch.model.service.StoreService;

public class AddItemTask extends BackgroundTask {

    private static final String LIST_ITEMS_TAG = "ListStoreItemsTask";

    private ServerFacade serverFacade;
    private final String itemName;

    public AddItemTask(String itemName, Handler messageHandler) {
        super(messageHandler);
        this.itemName = itemName;
    }

    @Override
    protected void runTask() {
        System.out.println("In runTask() in AddItemTask()");
        try {
            AddItemRequest request = new AddItemRequest(itemName, Cache.getInstance().getCurrUserAuthToken());
            AddItemResponse response = getServerFacade().addPlayerItem(request, StoreService.ADD_STORE_ITEMS_URL);
            if (response.isSuccess()) {
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LIST_ITEMS_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    ServerFacade getServerFacade() {
        if (serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }
}


