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
import com.example.todofromscratch.model.net.request.AddTaskRequest;
import com.example.todofromscratch.model.net.request.TasksRequest;
import com.example.todofromscratch.model.net.response.AddTaskResponse;
import com.example.todofromscratch.model.net.response.LoginResponse;
import com.example.todofromscratch.model.net.response.RegisterResponse;
import com.example.todofromscratch.model.net.response.TasksResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    //private static final String SERVER_URL = "http://10.37.192.68:8080";
    private static final String SERVER_URL = "http://192.168.15.172:8080";


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
    public TasksResponse getTasks(TasksRequest request, String urlPath) throws IOException, TweeterRemoteException {
        System.out.println("urlPath serverFacade storyResponse" + urlPath);
        System.out.println("request serverFacade storyResponse" + request);
        TasksResponse response = clientCommunicator.doPost(urlPath, request, null, TasksResponse.class);
        System.out.println(response);
        System.out.println(response.getTasks());
//        System.out.println(response.getStory().size());
        return response;
    }

    public AddTaskResponse addTask(AddTaskRequest request, String urlPath) throws IOException, TweeterRemoteException {
        System.out.println("Completed in serverFacade: " + request.getTask().completed);
        AddTaskResponse response = clientCommunicator.doPost(urlPath, request, null, AddTaskResponse.class);
        return response;
    }
//
//    public TaskResponse updateTask(TaskRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        TasksResponse response = clientCommunicator.doPost(urlPath, request, null, TasksResponse.class);
//        return response;
//    }

}

