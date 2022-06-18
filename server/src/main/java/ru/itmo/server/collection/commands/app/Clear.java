package ru.itmo.server.collection.commands.app;

import ru.itmo.common.general.User;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.PosgreSqlDAO;

public class Clear implements Command {

    PosgreSqlDAO psqlDAO = new PosgreSqlDAO();

    @Override
    public Response execute(Object arguments, User user) {
        psqlDAO.setUser(user);

        String all = psqlDAO.getAll();
        String[] numbers = all.trim().split(" ");

        for(String num: numbers) {
            psqlDAO.delete(Integer.parseInt(num));
        }
        return new Response(Response.Status.OK, null, null);
    }
}
