package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.Player;
import com.example.todofromscratch.model.domain.PlayerItem;
import com.example.todofromscratch.model.net.ServerFacade;
import com.example.todofromscratch.model.net.request.GetPlayerRequest;
import com.example.todofromscratch.model.net.request.ListPlayerItemsRequest;
import com.example.todofromscratch.model.net.response.GetPlayerResponse;
import com.example.todofromscratch.model.net.response.ListPlayerItemsResponse;
import com.example.todofromscratch.model.service.StoreService;
import com.example.todofromscratch.model.service.UserService;

import java.util.ArrayList;

public class GetPlayerTask extends BackgroundTask {

    private static final String GET_PLAYER_TAG = "GetPlayerTask";
    public static final String PLAYER_KEY = "Player";

    private ServerFacade serverFacade;
    private Player player;

    public GetPlayerTask(Handler messageHandler) {
        super(messageHandler);
    }

    @Override
    protected void runTask() {
        System.out.println("In runTask() in GetPlayerTask");
        try {
            GetPlayerRequest request = new GetPlayerRequest(Cache.getInstance().getCurrUserAuthToken());
            GetPlayerResponse response = getServerFacade().getPlayer(request, UserService.GET_PLAYER_URL_PATH);
            if (response.isSuccess()) {
                this.player = response.getPlayer();
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(GET_PLAYER_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(PLAYER_KEY, player);
    }

    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }


}