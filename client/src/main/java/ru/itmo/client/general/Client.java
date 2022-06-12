package ru.itmo.client.general;

import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.server.ServerAPI;
import ru.itmo.client.server.ServerAPImpl;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;

import java.io.IOException;

public class Client {
    private final String serverHost;
    private final int serverPort;
    private final ServerAPI serverAPI;
    private final int connectionAttempts = 20;
    private final int connectionTimeout = 2000;

    public Client(String serverHost, int serverPort){

        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.serverAPI = new ServerAPImpl(serverHost, serverPort);

        start();
    }

    public void start() {
        while (!serverAPI.connectToServer()) {
            if (serverAPI.getAttempts() > connectionAttempts) {
                ClientAppLauncher.log.fatal("Превышено количество попыток подключиться");
                return;
            }
            try {
                Thread.sleep(connectionTimeout);
            } catch (InterruptedException e) {
                ClientAppLauncher.log.error("Произошла ошибка при попытке ожидания подключения!");
                ClientAppLauncher.log.error("Повторное подключение будет произведено немедленно.");
            }
        }
    }

    public Response send(Request request)  {
        try {
            return serverAPI.sendToServer(request);
        } catch (IOException e) {
            start();
            send(request);
        }
        return null;
    }
}
