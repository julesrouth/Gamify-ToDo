package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.AuthToken;

public class TasksRequest {

        private AuthToken authtoken;

        private TasksRequest() {}

        public TasksRequest(AuthToken authtoken) {
            this.authtoken = authtoken;
        }

        public AuthToken getAuthtoken() {
            return authtoken;
        }

        public void setAuthtoken(AuthToken authtoken) {
            this.authtoken = authtoken;
        }

}
