package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.AuthToken;

public class ListPlayerItemsRequest {

    private AuthToken authtoken;

    public ListPlayerItemsRequest(AuthToken authToken) {
        this.authtoken = authToken;
    }

    public AuthToken getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authtoken = authToken;
    }
}
