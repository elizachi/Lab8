package ru.itmo.server.collection.commands.auth;

import ru.itmo.common.responses.Response;
import ru.itmo.common.general.User;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.UserDAO;

public class Authorization implements Command {

    private final UserDAO searchUser = new UserDAO();
    @Override
    public Response execute(Object arguments, User user) {
        User foundedUser = searchUser.get(user.getDecodeUser());
        if(foundedUser == null) {
            return new Response(Response.Status.UNKNOWN, null, null);
        } else if(foundedUser.getPassword() == null) {
            return new Response(Response.Status.WRONG, null, null);
        }
        return new Response(Response.Status.OK, null, foundedUser);
    }
}
