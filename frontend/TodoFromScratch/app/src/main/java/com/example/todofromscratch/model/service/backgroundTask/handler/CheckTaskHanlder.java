package com.example.todofromscratch.model.service.backgroundTask.handler;

import android.os.Message;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.Player;
import com.example.todofromscratch.model.service.backgroundTask.BackgroundTask;
import com.example.todofromscratch.model.service.backgroundTask.GetPlayerTask;
import com.example.todofromscratch.model.service.backgroundTask.observer.GeneralObserver;

public class CheckTaskHanlder extends MainHandler {

        private GeneralObserver observer;
        private GetPlayerTask task;
        public CheckTaskHanlder(GeneralObserver observer) {
            super();
            this.observer = observer;
        }

        @Override
        protected void updateFollow() {

        }

        @Override
        protected BackgroundTask getTaskType() {
            return task;
        }

        @Override
        protected void success(Message msg) {
//            Player player = (Player) msg.getData().getSerializable(GetPlayerTask.PLAYER_KEY);
//            Cache.getInstance().setCurrPlayer(player);
            try {
                observer.success("Success");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        @Override
        protected void error(String message) {
            observer.fail(message);
        }

        @Override
        protected void exception(Exception ex) {
            observer.fail(ex.getMessage());
        }

    }
