package ru.itmo.common.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;

public class Request {
    private CommandType command;
    private HumanBeing arguments;

    private User user;

    public Request(CommandType command, HumanBeing arguments, User user) {
        this.command = command;
        this.arguments = arguments;
        this.user = user;
    }
    public static Request fromJson(String json) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Request.class, new RequestAdapter());
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.fromJson(json, Request.class);
    }

    public String toJson() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Request.class, new RequestAdapter());
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.toJson(this);
    }

    public CommandType getCommand() {
        return this.command;
    }

    public HumanBeing getArguments() {
        return this.arguments;
    }

    public User getUser() {
        return this.user;
    }

    public void setCommand(CommandType command) {
        this.command = command;
    }

    public void setArguments(HumanBeing arguments) {
        this.arguments = arguments;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Request{\n" +
                "command=" + command +
                "\n, arguments=" + arguments +
                "\n, user=" + user.toString() +
                "\n}";
    }
}
