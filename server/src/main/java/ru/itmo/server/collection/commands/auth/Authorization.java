package ru.itmo.server.collection.commands.auth;

import ru.itmo.common.responses.Response;
import ru.itmo.common.general.User;
import ru.itmo.server.collection.commands.Command;

public class Authorization implements Command {
    @Override
    public Response execute(Object arguments, User user) {
        return new Response(Response.Status.OK, "Всё заебись", new User("", ""));
    }
}
