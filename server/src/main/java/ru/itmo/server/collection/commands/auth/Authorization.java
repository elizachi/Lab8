package ru.itmo.server.collection.commands.auth;

import ru.itmo.common.responses.Response;
import ru.itmo.common.general.User;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.UserDAO;

public class Authorization implements Command {
    @Override
    public Response execute(Object arguments, User user) {
        if(new UserDAO().get(user) == null) {
            return new Response(Response.Status.UNKNOWN, "Не нашлось", null);
        }
        return new Response(Response.Status.OK, "Всё заебись", new User("", "", ""));
    }
}
