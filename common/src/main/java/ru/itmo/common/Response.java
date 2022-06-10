package ru.itmo.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.itmo.common.http.Body;
import ru.itmo.common.http.Headers;
import ru.itmo.common.http.MethodType;

public class Response {

    public final MethodType method;

    private final String standard = " / HTTP/1.1";
    private final Headers headers;

    private final Body body;

    public Response(MethodType method, Headers headers, Body body) {
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public static Response fromJson(String json)  {
        return new Gson().fromJson(json, Response.class);
    }


//    public <T> T getArgumentAs(Class<T> clazz) {
//        return new Gson().fromJson((String) argument, clazz);
//    }
//
//    public <T> Object getArgumentAs(TypeToken<T> typeToken) {
//        return new Gson().fromJson((String) argument, typeToken.getType());
//    }

    public String toJson() {
        return new Gson().toJson(this);
    }

}
