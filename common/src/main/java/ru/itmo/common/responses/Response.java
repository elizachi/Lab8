package ru.itmo.common.responses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.requests.RequestAdapter;

public class Response {

    public final Status status;
    public final HumanBeing answer;
    public final User user;

    public Response(Status status, HumanBeing answer, User user) {
        this.status = status;
        this.answer = answer;
        this.user = user;
    }

    public static Response fromJson(String json)  {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Response.class, new ResponseAdapter());
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.fromJson(json, Response.class);
    }

    public String toJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Response.class, new ResponseAdapter());
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.toJson(this);
    }

    public enum Status {
        OK, // все прошло успешно
        ERROR, // произошла ошибка на стороне сервера
        UNKNOWN, // неизвестный логин
        WRONG, // неверный пароль при условии верного логина
        EXIST
    }

    public Status getStatus() {
        return this.status;
    }

    public User getUser() {
        return this.user;
    }

    public HumanBeing getAnswer() {
        return this.answer;
    }
}
