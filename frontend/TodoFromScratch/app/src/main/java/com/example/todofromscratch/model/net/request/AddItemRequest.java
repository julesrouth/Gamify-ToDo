package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.AuthToken;

public class AddItemRequest {

    private String itemName;
    private AuthToken authtoken;

    public AddItemRequest(String itemName, AuthToken authToken) {
        this.itemName = itemName;
        this.authtoken = authToken;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public AuthToken getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authtoken = authToken;
    }
}
