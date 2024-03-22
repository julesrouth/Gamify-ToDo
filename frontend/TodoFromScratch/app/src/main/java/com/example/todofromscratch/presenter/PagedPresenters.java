package com.example.todofromscratch.presenter;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.service.TaskService;
import com.example.todofromscratch.model.service.UserService;

import java.util.List;

public abstract class PagedPresenters<T> extends Presenter {

    private static final int PAGE_SIZE = 25;

    private T lastItem;

    private boolean isLoading;

    private boolean hasMorePages;
    private View view;
    private TaskService taskService;
    private UserService userService;

    public interface View<T> extends Presenter.View {

        void setLoadingFooter(boolean value);

        void addMoreItems(List<T> items);

        void startUserActivity(User user);
    }

    public PagedPresenters(View view) {
        super(view);
        this.view = view;
        taskService = new TaskService();
        userService = new UserService();
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isHasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setLastItem(T lastItem) {
        this.lastItem = lastItem;
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            getService(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        }
    }

    protected abstract void getService(AuthToken authtoken, User user, int PAGE_SIZE, T lastItem);

//    public void getUserStatus(TextView userAlias) {
//        userService.getUserStatus(Cache.getInstance().getCurrUserAuthToken(), userAlias, new GetUserObserver());
//    }
//
//    public void getUserStatusClickable(String clickable) {
//        userService.getUserStatusClickable(Cache.getInstance().getCurrUserAuthToken(), clickable, new GetUserObserver());
//    }
//
//    public void getUser(TextView userAlias) {
//        userService.getUser(Cache.getInstance().getCurrUserAuthToken(), userAlias, new GetUserObserver());
//    }

    public abstract class PagedTaskObserver {

        public void displayException(Exception ex) {
            setFooter();
            String exception = getExceptionMessage();
            view.displayMessage(exception + ex);
        }

        public void displayError(String message) {
            setFooter();
            view.displayMessage(message);
        }

        protected abstract String getExceptionMessage();

        public PagedTaskObserver() {
            super();
        }

        public void addMoreItems(List<T> items, boolean hasMorePages) {
            setLoading(false);
            view.setLoadingFooter(false);
            setHasMorePages(hasMorePages);
            setLastItem((items.size() > 0) ? items.get(items.size() - 1) : null);
            view.addMoreItems(items);
        }

        public void successPost(String message) {}

        protected void setFooter() {
            setLoading(false);
            view.setLoadingFooter(false);
        }

    }

//    private class GetUserObserver implements TaskObserver {
//
//        @Override
//        public void displayException(Exception ex) {
//            view.displayMessage(ex.getMessage());
//        }
//
//        @Override
//        public void displayError(String message) {
//            view.displayMessage(message);
//        }
//
//        @Override
//        public void startUserActivity(User user) {
//            view.startUserActivity((user));
//        }
//    }

}