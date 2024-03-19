package com.example.todofromscratch.model.net.request;

import com.example.todofromscratch.model.domain.authtoken;

public class TasksRequest {

        private authtoken authtoken;
//        private User targetUser;
//        private int limit;
//        private Task lastTask;

        private TasksRequest() {}

        public TasksRequest(authtoken authtoken) {
            this.authtoken = authtoken;
        }

//        public TasksRequest(AuthToken authToken, User targetUser, int limit, Task lastTask) {
//            this.authToken = authToken;
//            this.targetUser = targetUser;
//            this.limit = limit;
//            this.lastTask = lastTask;
//        }

        public authtoken getAuthtoken() {
            return authtoken;
        }

        public void setAuthtoken(authtoken authtoken) {
            this.authtoken = authtoken;
        }
//
//        public User getTargetUser() {
//            return targetUser;
//        }
//
//        public void setTargetUser(User targetUser) {
//            this.targetUser = targetUser;
//        }
//
//        public int getLimit() {
//            return limit;
//        }
//
//        public void setLimit(int limit) {
//            this.limit = limit;
//        }
//
//        public Task getLastTask() {
//            return lastTask;
//        }
//
//        public void setLastTask(Task lastTask) {
//            this.lastTask = lastTask;
//        }

}
