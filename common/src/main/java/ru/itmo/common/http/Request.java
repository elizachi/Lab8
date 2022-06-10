package ru.itmo.common.http;

public class Request {
    public final MethodType method;

    private final String standard = " / HTTP/1.1";
    private final Headers headers;

    private final Body body;

    public Request(MethodType method, Headers headers, Body body) {
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

}
