package ru.itmo.client;

import ru.itmo.client.server.ServerAPI;
import ru.itmo.client.server.ServerAPImpl;

public class Client {
    private final String serverHost;
    private final int serverPort;
    private final int connectionAttempts = 20;
    private final int connectionTimeout = 2000;

    public Client(String serverHost, int serverPort){

        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        ServerAPI serverAPI = new ServerAPImpl(serverHost, serverPort);

        while (!serverAPI.connectToServer()) {
            if (serverAPI.getAttempts() > connectionAttempts) {
                System.err.println("Превышено количество попыток подключиться");
                return;
            }
            try {
                Thread.sleep(connectionTimeout);
            } catch (InterruptedException e) {
                System.err.println("Произошла ошибка при попытке ожидания подключения!");
                System.err.println("Повторное подключение будет произведено немедленно.");
            }
        }
    }
}
