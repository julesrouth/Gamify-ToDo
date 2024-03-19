package com.example.todofromscratch.model.net;

// TaskType.java
public enum TaskType {
    DAILY("daily"),
    TASK("task"),
    WEEKLY("weekly");

    private final String value;

    TaskType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
