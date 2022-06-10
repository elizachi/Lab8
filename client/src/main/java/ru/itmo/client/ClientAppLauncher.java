package ru.itmo.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ClientAppLauncher extends Application {

    public static final Logger log = LogManager.getLogger(ClientAppLauncher.class.getName());

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("authorization-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Auth");
        stage.setScene(scene);
        stage.show();
        log.info("Launching the application");
    }

    public static void main(String[] args) {
        launch();
    }
}