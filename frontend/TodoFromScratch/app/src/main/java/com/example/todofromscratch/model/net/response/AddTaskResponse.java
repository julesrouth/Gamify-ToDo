package com.example.todofromscratch.model.net.response;

public class AddTaskResponse extends Response {

    public AddTaskResponse(String message) {
        super(false, message);
    }

    public AddTaskResponse() {
        super(true, null);
    }

}
