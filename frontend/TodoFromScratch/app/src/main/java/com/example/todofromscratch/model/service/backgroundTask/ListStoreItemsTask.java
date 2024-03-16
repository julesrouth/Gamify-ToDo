package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.StoreItem;
import com.example.todofromscratch.model.domain.StoreItemsList;
import com.example.todofromscratch.model.net.ServerFacade;
import com.example.todofromscratch.model.net.request.ListStoreItemsRequest;
import com.example.todofromscratch.model.net.response.ListStoreItemsResponse;
import com.example.todofromscratch.model.service.StoreService;

import java.util.ArrayList;

public class ListStoreItemsTask extends BackgroundTask {

    private static final String LIST_ITEMS_TAG = "ListStoreItemsTask";
    public static final String STORE_ITEMS_KEY = "StoreItems";

    private ServerFacade serverFacade;
    private StoreItemsList storeItems;

    public ListStoreItemsTask(Handler messageHandler) {
        super(messageHandler);
    }

    @Override
    protected void runTask() {
        System.out.println("In runTask() in ListStoreItemsTask()");
        try {
            ListStoreItemsRequest request = new ListStoreItemsRequest();
            ListStoreItemsResponse response = getServerFacade().listStoreItems(request, StoreService.LIST_STORE_ITEMS_URL);
            if (response.isSuccess()) {
                this.storeItems = response.getStoreItems();
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
        msgBundle.putSerializable(STORE_ITEMS_KEY, storeItems);
    }

    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }


}
