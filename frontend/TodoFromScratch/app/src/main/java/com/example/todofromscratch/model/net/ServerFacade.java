package com.example.todofromscratch.model.net;


import java.io.IOException;

import com.example.todofromscratch.model.net.request.AddItemRequest;
import com.example.todofromscratch.model.net.request.ListPlayerItemsRequest;
import com.example.todofromscratch.model.net.request.ListStoreItemsRequest;
import com.example.todofromscratch.model.net.request.LoginRequest;

import com.example.todofromscratch.model.net.request.RegisterRequest;
import com.example.todofromscratch.model.net.response.AddItemResponse;
import com.example.todofromscratch.model.net.response.ListPlayerItemsResponse;
import com.example.todofromscratch.model.net.response.ListStoreItemsResponse;
import com.example.todofromscratch.model.net.response.LoginResponse;
import com.example.todofromscratch.model.net.response.RegisterResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
//    private static final String SERVER_URL = "https://q8mr6p52na.execute-api.us-west-2.amazonaws.com/Test";
//    private static final String SERVER_URL = "http://127.0.0.1:5000";
//    private static final String SERVER_URL = "http://192.168.1.106:5000";
//    private static final String SERVER_URL = "http://192.168.39.105:5001";
//    private static final String SERVER_URL = "http://10.37.103.182:5001";
//    private static final String SERVER_URL = "http://10.37.196.176:5000";
//    private static final String SERVER_URL = "http://172.20.10.10:5000";
    private static final String SERVER_URL = "http://192.168.109.118:8080";


    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
    }

    public RegisterResponse register(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, RegisterResponse.class);
    }

    public ListStoreItemsResponse listStoreItems(ListStoreItemsRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, ListStoreItemsResponse.class);
    }

    public AddItemResponse addPlayerItem(AddItemRequest request, String urlPath)  throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, AddItemResponse.class);
    }

    public ListPlayerItemsResponse listPlayerItems(ListPlayerItemsRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, ListPlayerItemsResponse.class);
    }

}

