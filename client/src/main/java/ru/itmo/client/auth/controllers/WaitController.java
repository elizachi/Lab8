package ru.itmo.client.auth.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.general.Client;
import ru.itmo.client.general.ClientLoader;

import java.io.IOException;
import java.net.URL;

/**
 * Класс, отвечающий за окошко входа в приложение
 */
public class WaitController {
    @FXML
    private Label messageLabel;
    @FXML
    private Button reconnectButton;
    @FXML
    private AnchorPane waitForm;
    @FXML
    private URL location;
    @FXML
    private VBox authScene;

    @FXML
    private void initialize() {
        reconnectButton.setOnAction(event -> {
            closeWaitForm();
        });
    }

    public void openWaitForm() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("wait-form.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Wait");
            stage.setScene(scene);
            stage.show();
            ClientAppLauncher.log.info("Ожидание подключения...");
        } catch(IOException e) {
            throw new RuntimeException();
        }

    }

    public void closeWaitForm() {
        if(messageLabel != null) {
            messageLabel.getScene().getWindow().hide();
        }
    }

    public void ohNoMessage() {
        Label ohNoLabel = new Label("Connection failed :(");
        ohNoLabel.setLayoutX(81.0);
        ohNoLabel.setLayoutY(81.0);
        ohNoLabel.setPrefHeight(71.0);
        ohNoLabel.setPrefWidth(225.0);

        Button reconnectButton = new Button("Try to reconnect");
        reconnectButton.setLayoutX(123.0);
        reconnectButton.setLayoutY(152.0);
        reconnectButton.setPrefHeight(49.0);
        reconnectButton.setPrefWidth(125.0);

    }
}