package ru.itmo.client.utility;

import ru.itmo.client.exceptions.ForbiddenException;
import ru.itmo.common.http.Body;
import ru.itmo.common.http.Headers;
import ru.itmo.common.http.MethodType;
import ru.itmo.common.http.Request;
import ru.itmo.common.User;

public class Validator {

    public User checkAuth(String login, String password) throws ForbiddenException {
        User user = null;
        if(
                isCorrectString(login, false) && isCorrectString(password, false)
        ) {
            checkSymbols(password+login);

            Request request = new Request(
                    MethodType.GET,
                    new Headers().setAuthorization(login+":"+password),
                    null);

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
