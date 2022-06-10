package ru.itmo.client.server;

import ru.itmo.client.ClientAppLauncher;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerAPImpl implements ServerAPI {

    private final String serverHost;
    private final int serverPort;

    public int attempts = 0;
    Socket socket = new Socket();

    public ServerAPImpl(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    @Override
    public boolean connectToServer() {
        try {
            if (attempts > 0) {
                ClientAppLauncher.log.info("Попытка переподключиться...");
            }
            attempts++;
            socket = new Socket(serverHost, serverPort);
        } catch (UnknownHostException e) {
            ClientAppLauncher.log.error("Неизвестный хост: " + serverHost + ".\n");
            return false;
        } catch (IOException exception) {
            ClientAppLauncher.log.error("Ошибка открытия порта " + serverPort + ".\n");
            return false;
        }
        return true;
    }

    @Override
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAttempts() {
        return attempts;
    }


}
