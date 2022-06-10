package ru.itmo.client.server;

import ru.itmo.client.ClientAppLauncher;
import ru.itmo.common.Request;
import ru.itmo.common.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ServerAPImpl implements ServerAPI {

    private final String serverHost;
    private final int serverPort;

    public int attempts = 0;
    private Socket socket = new Socket();

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

    public Response sendToServer(Request request) throws IOException {

        socket.getOutputStream().write(request.toJson().getBytes(StandardCharsets.UTF_8));

        byte[] buffer = new byte[4096];
        int amount = socket.getInputStream().read(buffer);
        byte[] countPackage = new byte[amount];
        System.arraycopy(buffer, 0, countPackage, 0, amount);

        int count = Integer.parseInt(new String(countPackage, StandardCharsets.UTF_8));
        StringBuilder json = new StringBuilder();
        while(count != 0) {

            amount = socket.getInputStream().read(buffer);

            byte[] responseBytes = new byte[amount];
            System.arraycopy(buffer, 0, responseBytes, 0, amount);
            String add = new String(responseBytes, StandardCharsets.UTF_8);
            json.append(add);
            count--;
        }

        return Response.fromJson(json.toString());
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
