package ru.itmo.client.server;

import ru.itmo.common.Request;
import ru.itmo.common.Response;

import java.io.IOException;

public interface ServerAPI {
    boolean connectToServer();
    int getAttempts();
    void closeConnection();

    Response sendToServer(Request request) throws IOException;
}
