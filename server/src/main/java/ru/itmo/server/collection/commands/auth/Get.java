package ru.itmo.server.collection.commands.auth;

import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.PosgreSqlDAO;

public class Get implements Command {
    PosgreSqlDAO psqlDAO = new PosgreSqlDAO();
    @Override
    public Response execute(Object arguments, User user) {
        HumanBeing arg = (HumanBeing) arguments;
        HumanBeing human = psqlDAO.get(arg.getId());

        return new Response(Response.Status.OK, human, null);
    }
}
