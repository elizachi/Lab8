package ru.itmo.common;

import ru.itmo.common.User;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

    public enum MethodType {
        GET,
        POST,
        PATCH,
        DELETE,
        HEAD
    }

    public static class Headers {
        String authorization;

        public Headers setAuthorization(String auth) {
            this.authorization = encode(auth);
            return this;
        }

        private String encode(String data) {
            return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static class Body {
        User user;
        Object arguments;
    }
}
