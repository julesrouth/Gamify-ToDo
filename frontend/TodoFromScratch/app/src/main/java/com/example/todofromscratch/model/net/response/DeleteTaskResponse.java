package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.Player;
import com.example.todofromscratch.model.domain.Task;

public class DeleteTaskResponse extends Response {

//    public AddTaskResponse(String message) {
//        super(false, message);
//    }
//
//    public AddTaskResponse() {
//        super(true, null);
//    }


    public DeleteTaskResponse(Task task, String message) {
        super(true, null);
    }

    public DeleteTaskResponse(String message) {
        super(false, message);
    }


}
