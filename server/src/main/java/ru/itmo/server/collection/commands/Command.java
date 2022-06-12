package ru.itmo.server.collection.commands;

import ru.itmo.common.general.User;
import ru.itmo.common.responses.Response;

public interface Command {
    Response execute(Object arguments, User user);
}
