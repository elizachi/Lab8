package ru.itmo.server.collection.commands.auth;

import ru.itmo.common.general.User;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.UserDAO;

public class Registration implements Command {

    @Override
    public Response execute(Object arguments, User user) {
        int result = new UserDAO().add(user.getDecodeUser().hash());
        if(result == 1) {
            return new Response(Response.Status.OK, null, user);
        } else if(result == 0){
            return new Response(Response.Status.EXIST, null, null);
        } else {
            return new Response(Response.Status.ERROR, null, null);
        }
    }
}
