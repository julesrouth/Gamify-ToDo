package com.example.todofromscratch.model.net;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DifficultyTypeAdapter extends TypeAdapter<Difficulty> {
    @Override
    public void write(JsonWriter out, Difficulty value) throws IOException {
        out.value(value.name()); // Serialize enum to its name
    }

    @Override
    public Difficulty read(JsonReader in) throws IOException {
        return Difficulty.valueOf(in.nextString()); // Deserialize enum from its name
    }
}
