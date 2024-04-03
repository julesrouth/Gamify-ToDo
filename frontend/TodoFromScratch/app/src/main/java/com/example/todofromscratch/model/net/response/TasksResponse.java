package com.example.todofromscratch.model.net.response;

import com.example.todofromscratch.model.domain.Task;

import java.util.List;
import java.util.Objects;

public class TasksResponse extends PagedResponse {

    private List<Task> tasks;

    public TasksResponse(String message) {
        super(false, message, false);
    }

    public TasksResponse(List<Task> tasks, boolean hasMorePages) {
        super(true, hasMorePages);
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        System.out.println("statuses getStory storyResponse: " + tasks);
        System.out.println("statuses size getStory storyResponse: " + tasks.size());
        return tasks;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        TasksResponse that = (TasksResponse) param;

        return (Objects.equals(tasks, that.tasks) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks);
    }

}
