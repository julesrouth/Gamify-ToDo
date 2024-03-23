package com.example.todofromscratch.model.net;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class TaskTypeAdapter extends TypeAdapter<TaskType> {
    @Override
    public void write(JsonWriter out, TaskType value) throws IOException {
        out.value(value.name()); // Serialize enum to its name
    }

    @Override
    public TaskType read(JsonReader in) throws IOException {
        return TaskType.valueOf(in.nextString()); // Deserialize enum from its name
    }
}
