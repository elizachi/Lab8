package ru.itmo.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itmo.server.general.ServerLoader;
import ru.itmo.server.general.Server;

public class ServerLauncher {

    public static final Logger log = LogManager.getLogger(ServerLauncher.class.getName());

    public static void main(String[] args) {

        ServerLoader.setArgs(args);

        Server server = new Server(ServerLoader.getServerHost(), ServerLoader.getServerPort());
        server.start();
    }
}
