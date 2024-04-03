package com.example.todofromscratch.model.net;

// Difficulty.java
public enum Difficulty {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private final String value;

    Difficulty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
