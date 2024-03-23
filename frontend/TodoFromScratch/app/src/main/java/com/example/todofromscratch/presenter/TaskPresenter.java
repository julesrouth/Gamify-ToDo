package com.example.todofromscratch.presenter;

import com.example.todofromscratch.cache.Cache;
import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.domain.Task;
import com.example.todofromscratch.model.domain.User;
import com.example.todofromscratch.model.service.TaskService;

import java.util.List;

public class TaskPresenter extends PagedPresenters<Task>{

        public interface View extends PagedPresenters.View<Task> {

            void addMoreItems(List<Task> tasks);

        }

        private View view;
        private TaskService taskService;

    public TaskPresenter(View view) {
            super(view);
            this.view = view;
            taskService = new TaskService();
        }

        public class GetTaskObserver extends PagedPresenters.PagedTaskObserver {
            @Override
            protected String getExceptionMessage() {
                return "Failed to get feed because of exception: ";
            }
        }

        public class GetTaskObserver2 implements TaskService.GetTaskServiceObserver {

            public GetTaskObserver2() {
                super();
            }

            @Override
            public void addMoreItems(List<Task> items, boolean hasMorePages) {
                setLoading(false);
                view.setLoadingFooter(false);
                setHasMorePages(hasMorePages);
                System.out.println("Items in addMoreItems of TaskPresenter: " + items);
//                setLastItem((items.size() > 0) ? items.get(items.size() - 1) : null);
//                view.addMoreItems(items);
                // Null check for the items list
                if (items != null) {
                    System.out.println("Items in addMoreItems of TaskPresenter: " + items);

                    // Check if the items list is empty before accessing its last item
                    Task lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
                    setLastItem(lastItem);

                    // Call view method to add items
                    view.addMoreItems(items);
                } else {
                    // Handle the case when items list is null
                    System.out.println("Items list is null in addMoreItems of TaskPresenter");
                    // Optionally, you can display a message or perform other actions
                }
            }

            @Override
            public void successPost(String message) {}

            @Override
            public void displayException(Exception ex) {
                setFooter();
                String exception = "story exception";
                view.displayMessage(exception + ex);
            }

            @Override
            public void displayError(String message) {
                setFooter();
                view.displayMessage(message);
            }

            protected void setFooter() {
                setLoading(false);
                view.setLoadingFooter(false);
            }
        }

        @Override
        protected void getService(AuthToken authtoken, User user, int PAGE_SIZE, Task lastItem) {
//            TaskService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem, new GetTaskObserver2());
        }

    public void getTasks() {
        TaskService taskService = new TaskService();
        taskService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), 25, null, new GetTaskObserver2());
    }

    public void addTask(Task newTask) {
        TaskService taskService = new TaskService();
        taskService.addTask(Cache.getInstance().getCurrUserAuthToken(), newTask, new GetTaskObserver2());
    }
}
