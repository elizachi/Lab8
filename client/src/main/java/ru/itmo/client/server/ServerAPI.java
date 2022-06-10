package ru.itmo.client.server;

public interface ServerAPI {
    boolean connectToServer();
    int getAttempts();
    void closeConnection();
}
