package ru.itmo.client.general;

import ru.itmo.client.ClientAppLauncher;

public class Loader {

    private static String[] args;

    private static String serverHost;
    private static int serverPort;

    public static void setArgs(String[] newArgs) {
        args = newArgs;
        loadArgs();
    }
    private static void loadArgs() {
        try {
            serverHost = args[0].trim();
            serverPort = Integer.parseInt(args[1].trim());
            ClientAppLauncher.log.info("Получены хост: " + serverHost + " и порт: " + serverPort);
        } catch (NumberFormatException exception){
            ClientAppLauncher.log.fatal("Порт должен быть числом.");
        } catch (ArrayIndexOutOfBoundsException exception){
            ClientAppLauncher.log.fatal("Недостаточно аргументов.");
        }
    }

    public static String getServerHost() {
        return serverHost;
    }

    public static int getServerPort() {
        return serverPort;
    }
}
