package ru.itmo.client.auth.utility;

import javafx.scene.paint.Paint;
import ru.itmo.client.auth.exceptions.AuthException;
import ru.itmo.client.auth.exceptions.CheckUserException;
import ru.itmo.client.general.Client;
import ru.itmo.client.general.ClientLoader;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.common.general.User;

public class AuthValidator {

    private final Client client = new Client(ClientLoader.getServerHost(), ClientLoader.getServerPort());

    public User checkAuth(String login, String password) throws CheckUserException, AuthException {

        checkLogin(login);

        checkPassword(password);

        Response response = checkUser(
                new User(login, password, null)
        );

        return scanStatus(response);
    }

    public User checkReg(String login, String password, Paint color) throws CheckUserException, AuthException {

        checkLogin(login);

        checkPassword(password);

        Response response = addUser(
                new User(login, password, color.toString())
        );

        return scanStatus(response);
    }

    private void checkLogin(String login) throws CheckUserException {
        if(login.isEmpty()) {
            throw new CheckUserException(
                    CheckUserException.ErrorType.EMPTY.setTitle(
                            "Поле логин пусто."
                    )
            );
        } else if(login.contains(":")) {
            throw new CheckUserException(
                    CheckUserException.ErrorType.FORBIDDEN.setTitle(
                            "В поле логин были введены запрещённые символы."
                    )
            );
        }
    }

    private void checkPassword(String password) throws CheckUserException {
        if(password.isEmpty()) {
            throw new CheckUserException(
                    CheckUserException.ErrorType.EMPTY.setTitle(
                            "Поле пароль пусто."
                    )
            );
        } else if(password.contains(":")) {
            throw new CheckUserException(
                    CheckUserException.ErrorType.FORBIDDEN.setTitle(
                            "В поле пароль были введены запрещённые символы."
                    )
            );
        } else if(password.length() < 8) {
            throw new CheckUserException(
                    CheckUserException.ErrorType.UNSAFE.setTitle(
                            "Поле пароль должно содержать минимум 8 символов."
                    )
            );
        }
    }

    private Response checkUser(User user) {
        Request request = new Request(
                CommandType.AUTHORIZATION,
                null,
                user
        );

        return client.send(request);
    }

    private Response addUser(User user) {
        Request request = new Request(
                CommandType.REGISTRATION,
                null,
                user
        );

        return client.send(request);
    }

    private User scanStatus(Response response) throws AuthException {
        if(Response.Status.OK == response.getStatus()) {
            return response.getUser();
        } else if(Response.Status.UNKNOWN == response.getStatus()) {
            throw new AuthException(
                    AuthException.ErrorType.UNKNOWN.setTitle(
                            "Пользователя с таким логином не существует.\n" +
                                    "Вы можете создать новый аккаунт перейдя во вкладку Регистрация"
                    )
            );
        } else if(Response.Status.WRONG == response.getStatus()) {
            throw new AuthException(
                    AuthException.ErrorType.WRONG.setTitle(
                            "Пароль введён неверно."
                    )
            );
        } else if(Response.Status.EXIST == response.getStatus()) {
            throw new AuthException(
                    AuthException.ErrorType.EXIST.setTitle(
                            "Пользователь с таким логином или цветом уже существует.\n" +
                                    "Пожалуйста, повторите попытку регистрации."
                    )
            );
        }else {
            throw new AuthException(
                    AuthException.ErrorType.ERROR.setTitle(
                            "В процессе авторияции произошла ошибка, пожалуйста, повторите попытку чуть позже."
                    )
            );
        }
    }
}
