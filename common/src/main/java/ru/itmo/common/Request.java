package ru.itmo.common;

public class Request {
    public final MethodType method;
    public final Object argument;
    private final User user;
    private final String standard = " / HTTP/1.1";
    private Headers headers;

    public Request(MethodType method, Object argument, User user) {
        this.method = method;
        this.argument = argument;
        this.user = user;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

}
