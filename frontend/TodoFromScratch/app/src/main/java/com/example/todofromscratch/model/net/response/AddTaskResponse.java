package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.domain.authtoken;

public class AddTaskResponse extends Response {

    public AddTaskResponse(String message) {
        super(false, message);
    }

    public AddTaskResponse() {
        super(true, null);
    }

}
