package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.authtoken;

public class TasksRequest {

        private authtoken authtoken;

        private TasksRequest() {}

        public TasksRequest(authtoken authtoken) {
            this.authtoken = authtoken;
        }

        public authtoken getAuthtoken() {
            return authtoken;
        }

        public void setAuthtoken(authtoken authtoken) {
            this.authtoken = authtoken;
        }

}
