package ru.itmo.server.collection.commands;

import ru.itmo.common.Response;
import ru.itmo.common.User;

public interface Command {
    Response execute(Object body);
}
