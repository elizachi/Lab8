package ru.itmo.server.collection.commands.app;

import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.PosgreSqlDAO;

public class Add implements Command {
    PosgreSqlDAO psqlDAO = new PosgreSqlDAO();
    @Override
    public Response execute(Object arguments, User user) {
        psqlDAO.setUser(user);

        HumanBeing humanBeing = (HumanBeing) arguments;

        int id = psqlDAO.add(humanBeing);

        if(id != -1) {
            humanBeing.setId(id);

            return new Response(Response.Status.OK, null, user);
        }
        return new Response(Response.Status.ERROR, null, null);
    }
}
