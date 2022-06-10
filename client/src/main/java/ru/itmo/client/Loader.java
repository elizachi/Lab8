package ru.itmo.client;

public class Loader implements Runnable{

    private final String[] args;

    public Loader(String[] args) {
        this.args = args;
        loadArgs();
    }

    @Override
    public void run() {
        loadArgs();
    }
    private void loadArgs() {
        try {
            String serverHost = args[0].trim();
            int serverPort = Integer.parseInt(args[1].trim());
            ClientAppLauncher.log.info("Получены хост: " + serverHost + " и порт: " + serverPort);

            new Client(serverHost, serverPort).start();

        } catch (NumberFormatException exception){
            ClientAppLauncher.log.fatal("Порт должен быть числом.");
            return;
        } catch (ArrayIndexOutOfBoundsException exception){
            ClientAppLauncher.log.fatal("Недостаточно аргументов.");
            return;
        }
    }
}
