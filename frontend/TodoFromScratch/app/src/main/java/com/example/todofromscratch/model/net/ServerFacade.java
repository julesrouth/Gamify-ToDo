package com.example.todofromscratch.model.net;


import java.io.IOException;

//import edu.byu.cs.tweeter.model.net.request.FeedRequest;
//import edu.byu.cs.tweeter.model.net.request.FollowRequest;
//import edu.byu.cs.tweeter.model.net.request.FollowerCountRequest;
//import edu.byu.cs.tweeter.model.net.request.FollowerRequest;
//import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
//import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
//import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import com.example.todofromscratch.model.net.request.LoginRequest;
//import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
//import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import com.example.todofromscratch.model.net.request.RegisterRequest;
//import edu.byu.cs.tweeter.model.net.request.StoryRequest;
//import edu.byu.cs.tweeter.model.net.request.UnFollowRequest;
//import edu.byu.cs.tweeter.model.net.request.UserRequest;
//import edu.byu.cs.tweeter.model.net.response.FeedResponse;
//import edu.byu.cs.tweeter.model.net.response.FollowResponse;
//import edu.byu.cs.tweeter.model.net.response.FollowerCountResponse;
//import edu.byu.cs.tweeter.model.net.response.FollowerResponse;
//import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
//import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
//import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import com.example.todofromscratch.model.net.request.AddTaskRequest;
import com.example.todofromscratch.model.net.request.TasksRequest;
import com.example.todofromscratch.model.net.response.AddTaskResponse;
import com.example.todofromscratch.model.net.response.LoginResponse;
//import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
//import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import com.example.todofromscratch.model.net.response.RegisterResponse;
import com.example.todofromscratch.model.net.response.TasksResponse;
//import edu.byu.cs.tweeter.model.net.response.StoryResponse;
//import edu.byu.cs.tweeter.model.net.response.UnFollowResponse;
//import edu.byu.cs.tweeter.model.net.response.UserResponse;

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
//    private static final String SERVER_URL = "http://10.37.137.246:5001";
    private static final String SERVER_URL = "http://10.37.48.61:5001";


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

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
//    public FollowingResponse getFollowees(FollowingRequest request, String urlPath)
//            throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
//    }
//
//    public FollowerResponse getFollowers(FollowerRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, FollowerResponse.class);
//    }
//
//    public FollowerCountResponse getFollowersCount(FollowerCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, FollowerCountResponse.class);
//    }

//    public FollowingCountResponse getFollowingCount(FollowingCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, FollowingCountResponse.class);
//    }
//
//    public FollowResponse Follow(FollowRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, FollowResponse.class);
//    }
//
//    public UnFollowResponse UnFollow(UnFollowRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, UnFollowResponse.class);
//    }

//    public PostStatusResponse postStatus(PostStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, PostStatusResponse.class);
//    }
//
//    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, LogoutResponse.class);
//    }
//
//    public IsFollowerResponse isFollower(IsFollowerRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, IsFollowerResponse.class);
//    }
//
//    public FeedResponse getFeed(FeedRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, FeedResponse.class);
//    }
//
//    public UserResponse getUser(UserRequest request, String urlPath) throws IOException, TweeterRemoteException {
//        return clientCommunicator.doPost(urlPath, request, null, UserResponse.class);
//    }

    public RegisterResponse register(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, request, null, RegisterResponse.class);
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

