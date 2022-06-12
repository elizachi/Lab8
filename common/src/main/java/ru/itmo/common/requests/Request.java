package ru.itmo.common.requests;

import com.google.gson.Gson;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;

public class Request {
    public CommandType command;
    public Object arguments;

    public User user;

    public Request(CommandType command, Object arguments, User user) {
        this.command = command;
        this.arguments = new Gson().toJson(arguments);
        this.user = user;
    }
    public static Object fromJson(String json) {
        return new Gson().fromJson(json, Object.class);

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

    public CommandType getCommand() {
        return this.command;
    }

    public Object getArguments() {
        return this.arguments;
    }

    public User getUser() {
        return this.user;
    }

    public void setCommand(CommandType command) {
        this.command = command;
    }

    public void setArguments(Object arguments) {
        this.arguments = arguments;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command=" + command +
                ", arguments=" + arguments +
                ", user=" + user.toString() +
                '}';
    }
}
