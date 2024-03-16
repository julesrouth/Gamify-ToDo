package com.example.todofromscratch.presenter;

import android.util.Log;

import com.example.todofromscratch.model.service.StoreService;
import com.example.todofromscratch.model.service.backgroundTask.observer.GeneralObserver;

public class StorePresenter implements GeneralObserver {

    public interface View{
        void showInfoMessage(String message);
        void taskSuccess(String message);
        void taskFail(String message);
    }

    private final View view;

    public StorePresenter(View view) {
        this.view = view;
    }

    @Override
    public void success(String message) {
        view.taskSuccess(message);
    }

    @Override
    public void fail(String message) {
        view.taskFail("Failed: " + message);
    }

    public void getStoreItems() {
        view.showInfoMessage("Getting store items...");
        Log.i("STORE_PRESENTER", "Getting list of store items");
        StoreService storeService = new StoreService();
        storeService.getStoreItems(this);
    }

    public void addItem(String itemName) {
        view.showInfoMessage("Purchasing a " + itemName);
        Log.i("STORE_PRESENTER", "Purchasing an item");
        StoreService storeService = new StoreService();
        storeService.addItem(itemName, this);
    }

    public void listPlayerItems() {
        Log.i("STORE_PRESENTER_NEW", "Getting list of player's items");
        StoreService storeService = new StoreService();
        storeService.listPlayerItems(this);
    }
}
