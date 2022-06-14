package ru.itmo.server.collection.commands;

import ru.itmo.common.general.User;
import ru.itmo.common.responses.Response;

public interface Command<T> {
    Response execute(T arguments, User user);
}
