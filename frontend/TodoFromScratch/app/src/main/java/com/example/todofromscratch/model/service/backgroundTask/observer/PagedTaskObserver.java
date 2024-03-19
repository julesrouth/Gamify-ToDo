package com.example.todofromscratch.model.service.backgroundTask.observer;

import java.util.List;

public interface PagedTaskObserver<T> extends TaskObserver{

    void addMoreItems(List<T> items, boolean hasMorePages);

}
