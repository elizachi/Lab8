package ru.itmo.server.collection.commands.auth;

import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.PosgreSqlDAO;

public class Load implements Command {
    PosgreSqlDAO psqlDAO = new PosgreSqlDAO();
    @Override
    public Response execute(Object arguments, User user) {
        String res = psqlDAO.getAll();

        if(res.isBlank()) {
            return new Response(Response.Status.ERROR, null, null);
        } else {
            return new Response(Response.Status.OK, new HumanBeing(res, null, null, 0,
                    false, null, null, null, null), null);
        }
    }
}
