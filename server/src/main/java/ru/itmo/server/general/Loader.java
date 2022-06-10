package ru.itmo.server.general;

import ru.itmo.server.ServerLauncher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Loader {

    private static String[] args;

    private static String serverHost;
    private static int serverPort;
    private static Properties properties = new Properties();

    public static void setArgs(String[] newArgs) {
        args = newArgs;
        loadArgs();
        loadProperties();
    }
    private static void loadArgs() {
        try {
            serverHost = args[0].trim();
            serverPort = Integer.parseInt(args[1].trim());
            ServerLauncher.log.info("Получены хост: " + serverHost + " и порт: " + serverPort);
        } catch (NumberFormatException exception){
            ServerLauncher.log.fatal("Порт должен быть числом.");
        } catch (ArrayIndexOutOfBoundsException exception){
            ServerLauncher.log.fatal("Недостаточно аргументов.");
        }
    }

    public static String getServerHost() {
        return serverHost;
    }

    public static int getServerPort() {
        return serverPort;
    }

    private static void loadProperties() {
        try(InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("storage.properties")) {
            properties.load(in);
            ServerLauncher.log.info("Дополнительные настройки для подключения к базе данных получены");
        } catch (IOException e) {
            ServerLauncher.log.fatal("Произошла ошибка поиска файла настроек. Пожалуйста, проверьте наличие файла.");
        }
    }
    public String getURL() {
        return properties.getProperty("url");
    }
    public String getUserName() {
        return properties.getProperty("userName");
    }
    public String getPassword() {
        return properties.getProperty("password");
    }
    public String getDriver() {
        return properties.getProperty("driver");
    }
}
