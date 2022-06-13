package ru.itmo.client.auth.utility;

import ru.itmo.client.auth.exceptions.AuthException;
import ru.itmo.client.auth.exceptions.CheckUserException;
import ru.itmo.client.general.Client;
import ru.itmo.client.general.Loader;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.common.general.User;

public class AuthValidator {

    private final Client client = new Client(Loader.getServerHost(), Loader.getServerPort());

    public User checkAuth(String login, String password) throws CheckUserException, AuthException {

        checkLogin(login);

        checkPassword(password);

        Response response = checkUser(new User(login, password, null));

        return scanStatus(response);
    }

    private void checkLogin(String login) throws CheckUserException {
        if(login.isEmpty()) {
            throw new CheckUserException(CheckUserException.ErrorType.EMPTY.setTitle("логин"));
        } else if(login.contains(":")) {
            throw new CheckUserException(CheckUserException.ErrorType.FORBIDDEN.setTitle("логин"));
        }
    }

    private void checkPassword(String password) throws CheckUserException {
        if(password.isEmpty()) {
            throw new CheckUserException(CheckUserException.ErrorType.EMPTY.setTitle("пароль"));
        } else if(password.contains(":")) {
            throw new CheckUserException(CheckUserException.ErrorType.FORBIDDEN.setTitle("пароль"));
        } else if(password.length() < 8) {
            throw new CheckUserException(CheckUserException.ErrorType.UNSAFE.setTitle("пароль"));
        }
    }

    private Response checkUser(User user) {
        Request request = new Request(
                CommandType.AUTHORIZATION,
                new HumanBeing(),
                user
        );

        return client.send(request);
    }

    private User scanStatus(Response response) throws AuthException {
        if(Response.Status.OK == response.getStatus()) {
            return response.getUser();
        } else if(Response.Status.UNKNOWN == response.getStatus()) {
            throw new AuthException(AuthException.ErrorType.UNKNOWN.setTitle("логин"));
        } else if(Response.Status.WRONG == response.getStatus()) {
            throw new AuthException(AuthException.ErrorType.WRONG.setTitle("логин"));
        } else {
            throw new AuthException(AuthException.ErrorType.ERROR.setTitle("логин"));
        }
    }
}
