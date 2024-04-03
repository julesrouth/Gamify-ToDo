package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.AuthToken;

public class GetPlayerRequest {

    private AuthToken authtoken;

    public GetPlayerRequest(AuthToken authToken) {
        this.authtoken = authToken;
    }

    public AuthToken getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(AuthToken authtoken) {
        this.authtoken = authtoken;
    }
}
