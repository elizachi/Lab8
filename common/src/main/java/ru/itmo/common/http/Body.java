package ru.itmo.common.http;

import com.google.gson.Gson;
import ru.itmo.common.CommandType;
import ru.itmo.common.User;

public class Body {

    private final CommandType command;
    private final User user;
    private final Object arguments;

    public Body(CommandType command, Object arguments, User user) {
        this.command = command;
        this.arguments = new Gson().toJson(arguments);
        this.user = user;
    }

    public CommandType getCommand() {
        return this.command;
    }
    public <T> T getArgumentAs(Class<T> clazz) {
        return new Gson().fromJson((String) arguments, clazz);
    }
}
