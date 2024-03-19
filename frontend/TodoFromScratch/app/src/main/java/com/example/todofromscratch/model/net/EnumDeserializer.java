package com.example.todofromscratch.model.net;

import com.google.gson.*;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class EnumDeserializer<T extends Enum<T>> implements JsonDeserializer<T> {

    private final Class<T> enumClass;

    public EnumDeserializer(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Enum.valueOf(enumClass, json.getAsString());
        } catch (IllegalArgumentException e) {
            // Handle invalid enum values gracefully
            return null;
        }
    }
}

