package ru.itmo.server.collection.commands.app;

import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.PosgreSqlDAO;

public class Delete implements Command {

    PosgreSqlDAO psqlDAO = new PosgreSqlDAO();

    @Override
    public Response execute(Object arguments, User user) {
        psqlDAO.setUser(user);

        HumanBeing human = (HumanBeing) arguments;

        if(psqlDAO.delete(human.getId())) {
            return new Response(Response.Status.OK, null, user);
        } else {
            return new Response(Response.Status.ERROR, null, user);
        }
    }
}
