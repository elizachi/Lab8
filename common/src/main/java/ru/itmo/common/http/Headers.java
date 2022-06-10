package ru.itmo.common.http;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Headers {
    String authorization;

    public Headers setAuthorization(String auth) {
        this.authorization = encode(auth);
        return this;
    }

    private String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }
}
