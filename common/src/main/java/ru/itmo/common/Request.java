package ru.itmo.common;

import com.google.gson.Gson;

import ru.itmo.common.http.Body;
import ru.itmo.common.http.Headers;
import ru.itmo.common.http.MethodType;

public class Request {
    public final MethodType method;

    private final String standard = " / HTTP/1.1";
    private final Headers headers;

    private final Body body;

    public Request(MethodType method, Headers headers, Body body) {
        this.method = method;
        this.headers = headers;
        this.body = body;
    }
    public static Request fromJson(String json) {
        return new Gson().fromJson(json, Request.class);
    }

//    public <T> T getArgumentAs(Class<T> clazz) {
//        return new Gson().fromJson((String) argument, clazz);
//    }

//    public <T> Object getArgumentAs(TypeToken<T> typeToken) {
//        return new Gson().fromJson((String) argument, typeToken.getType());
//    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public Body getBody() {
        return this.body;
    }
}
