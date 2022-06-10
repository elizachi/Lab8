package ru.itmo.client.utility;

import ru.itmo.common.User;

public class Validator {

    public User checkAuth(String login, String password) {
        User user = null;
        if(
                isCorrectString(login, false) && isCorrectString(password, false)
        ) {

        }
        return user;
    }

    private boolean isCorrectString(String str, boolean isNull) {
        if(str.isEmpty() && isNull) return true;
        return !str.isEmpty();
    }
}
