package ru.itmo.common;

import ru.itmo.common.MethodType;

public class Request {
    public final MethodType method;
    public final Object argument;
    private final User user;

    public Request(MethodType method, Object argument, User user) {
        this.method = method;
        this.argument = argument;
        this.user = user;
    }

}
