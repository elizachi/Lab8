package ru.itmo.server.collection.commands.auth;

import ru.itmo.common.Response;
import ru.itmo.common.User;
import ru.itmo.server.collection.commands.Command;

public class Authorization implements Command {
    @Override
    public Response execute(Object arguments) {
        return new Response(Response.Status.OK, "Всё заебись", new User("", ""));
    }
}
