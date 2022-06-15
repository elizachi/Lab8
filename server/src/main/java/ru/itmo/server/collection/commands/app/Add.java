package ru.itmo.server.collection.commands.app;

import ru.itmo.common.general.User;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.Command;

public class Add implements Command {
    @Override
    public Response execute(Object arguments, User user) {
        System.out.println("Ура ты безболезненно дошла до команды адд!!!");
        return new Response(Response.Status.OK, null, user);
    }
}
