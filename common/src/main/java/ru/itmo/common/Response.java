package ru.itmo.common;

import com.google.gson.Gson;

public class Response {

    public final Status status;
    public final String answer;
    public final User user;

    public Response(Status status, String answer, User user) {
        this.status = status;
        this.answer = answer;
        this.user = user;
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

    public enum Status {
        OK, // все прошло успешно
        ERROR, // произошла ошибка на стороне сервера
        UNKNOWN, // неизвестный логин
        WRONG // неверный пароль при условии верного логина
    }

    public Status getStatus() {
        return this.status;
    }

    public User getUser() {
        return this.user;
    }
}
