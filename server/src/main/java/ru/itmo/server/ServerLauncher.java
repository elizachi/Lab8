package ru.itmo.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLauncher {

    public static final Logger log = LogManager.getLogger(ServerLauncher.class.getName());

    public static void main(String[] args) {

        Loader.setArgs(args);

    }
}
