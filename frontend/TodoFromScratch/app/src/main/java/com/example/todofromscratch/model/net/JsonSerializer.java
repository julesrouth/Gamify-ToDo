package com.example.todofromscratch.model.net;


import com.example.todofromscratch.model.domain.AuthToken;
import com.example.todofromscratch.model.net.response.LoginResponse;
import com.google.gson.Gson;
import com.example.todofromscratch.model.domain.User;
public class JsonSerializer {

    public static String serialize(Object requestInfo) {
        System.out.println("Serializing");
        return (new Gson()).toJson(requestInfo);
    }

    public static <T> T deserialize(String value, Class<T> returnType) {
        System.out.println("Deserializing");
        System.out.println(value);
        return (new Gson()).fromJson(value, returnType);
    }
}