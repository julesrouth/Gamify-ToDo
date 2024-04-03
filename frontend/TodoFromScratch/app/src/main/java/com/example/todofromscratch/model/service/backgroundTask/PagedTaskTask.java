package com.example.todofromscratch.model.service.backgroundTask;

import android.os.Handler;

import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.User;

import java.util.List;

public abstract class PagedTaskTask extends PagedTask<Task> {

    protected PagedTaskTask(AuthToken authtoken, User targetUser, int limit, Task lastItem, Handler messageHandler) {
        super(authtoken, targetUser, limit, lastItem, messageHandler);
    }

    @Override
    protected final List<User> getUsersForItems(List<Task> items) {
//        return items.stream().map(x -> x.userId).collect(Collectors.toList());
        return null;
    }
}