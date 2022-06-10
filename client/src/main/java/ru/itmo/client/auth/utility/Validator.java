package ru.itmo.client.auth.utility;

import ru.itmo.client.auth.exceptions.ForbiddenException;
import ru.itmo.client.general.Client;
import ru.itmo.client.general.Loader;
import ru.itmo.common.Request;
import ru.itmo.common.User;
import ru.itmo.common.http.Headers;
import ru.itmo.common.http.MethodType;

public class Validator {

    private final Client client = new Client(Loader.getServerHost(), Loader.getServerPort());

    public User checkAuth(String login, String password) throws ForbiddenException {
        client.start();
        User user = null;
        if(
                isCorrectString(login, false) && isCorrectString(password, false)
        ) {
            checkSymbols(password+login);

            Request request = new Request(
                    MethodType.GET,
                    new Headers().setAuthorization(login+":"+password),
                    null);

            client.send(request);

            // TODO поменять сюда на челика который приходит с сервера
            user = new User(login, password);
        }
        return user;
    }

    private boolean isCorrectString(String str, boolean isNull) {
        if(str.isEmpty() && isNull) return true;
        return !str.isEmpty();
    }

    private void checkSymbols(String str) throws ForbiddenException {
        if(str.contains(":")) throw new ForbiddenException();
    }

}
