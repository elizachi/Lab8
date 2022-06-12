package ru.itmo.client.server;

import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;

import java.io.IOException;

public interface ServerAPI {
    boolean connectToServer();
    int getAttempts();
    void closeConnection();

    Response sendToServer(Request request) throws IOException;
}
