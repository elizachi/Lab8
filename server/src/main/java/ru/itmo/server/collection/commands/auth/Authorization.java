package ru.itmo.server.collection.commands.auth;

import ru.itmo.common.responses.Response;
import ru.itmo.common.general.User;
import ru.itmo.server.collection.commands.Command;
import ru.itmo.server.collection.dao.UserDAO;

public class Authorization implements Command {

    private final UserDAO searchUser = new UserDAO();
    @Override
    public Response execute(Object arguments, User user) {
        User foundedUser = searchUser.get(user);
        if(foundedUser == null) {
            System.out.println("Не нашлось пользователя с таким логином");
            return new Response(Response.Status.UNKNOWN, "Не нашлось пользователя с таким логином", null);
        } else if(foundedUser.getPassword() == null) {
            System.out.println("Неверный пароль");
            return new Response(Response.Status.WRONG, "Неверный пароль", null);
        }
        System.out.println("Пользователь успешно найден");
        return new Response(Response.Status.OK, "Пользователь успешно найден", foundedUser);
    }
}
