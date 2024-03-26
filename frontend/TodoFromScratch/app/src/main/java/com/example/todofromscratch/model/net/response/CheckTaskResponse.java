package com.example.todofromscratch.model.net.response;

public class CheckTaskResponse extends Response {

    public CheckTaskResponse(String message) {
        super(false, message);
    }

    public CheckTaskResponse() {
        super(true, null);
    }
}
