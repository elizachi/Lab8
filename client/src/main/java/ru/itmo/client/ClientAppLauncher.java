package ru.itmo.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ClientAppLauncher extends Application {

    private static String ServerHost;
    private static int ServerPort;

    public static final Logger log = LogManager.getLogger(ClientAppLauncher.class.getName());

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("authorization-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Auth");
        stage.setScene(scene);
        stage.show();
        log.info("Запуск приложения...");
    }

    public static void main(String[] args) {

        try {
            ServerHost = args[0].trim();
            ServerPort = Integer.parseInt(args[1].trim());
            log.info("Получены хост: " + ServerHost + " и порт: " + ServerPort);
        } catch (NumberFormatException exception){
            log.fatal("Порт должен быть числом");
            return;
        } catch (ArrayIndexOutOfBoundsException exception){
            log.fatal("Недостаточно аргументов командной строки в настройках конфигурации");
            return;
        }

        launch();
    }
}