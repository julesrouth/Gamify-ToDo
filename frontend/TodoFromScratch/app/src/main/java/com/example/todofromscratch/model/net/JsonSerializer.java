package com.example.todofromscratch.model.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer {

    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Register custom type adapters here
        gsonBuilder.registerTypeAdapter(TaskType.class, new EnumDeserializer<>(TaskType.class));
        gsonBuilder.registerTypeAdapter(Difficulty.class, new EnumDeserializer<>(Difficulty.class));
        gson = gsonBuilder.create();
    }

    public static String serialize(Object requestInfo) {
        System.out.println("Serializing");
        return gson.toJson(requestInfo);
    }

    public static <T> T deserialize(String value, Class<T> returnType) {
        System.out.println("Deserializing");
        System.out.println(value);
        return gson.fromJson(value, returnType);
    }

//    private static Gson gson;
//
//    static {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        // Register custom type adapters here
//        gsonBuilder.registerTypeAdapter(TaskType.class, new TaskTypeAdapter());
//        gsonBuilder.registerTypeAdapter(Difficulty.class, new DifficultyTypeAdapter());
//        gson = gsonBuilder.create();
//    }
//
//    public static String serialize(Object requestInfo) {
//        System.out.println("Serializing");
//        return gson.toJson(requestInfo);
//    }
//
//    public static <T> T deserialize(String value, Class<T> returnType) {
//        System.out.println("Deserializing");
//        System.out.println(value);
//        return gson.fromJson(value, returnType);

//    public static String serialize(Object requestInfo) {
//        System.out.println("Serializing");
//        return (new Gson()).toJson(requestInfo);
//    }
//
//    public static <T> T deserialize(String value, Class<T> returnType) {
//        System.out.println("Deserializing");
//        System.out.println(value);
//        return (new Gson()).fromJson(value, returnType);
//    }
}