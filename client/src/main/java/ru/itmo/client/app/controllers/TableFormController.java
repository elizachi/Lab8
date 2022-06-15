package ru.itmo.client.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;

import java.io.IOException;

public class TableFormController {

    @FXML
    private AnchorPane mainWindow;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuButton settingsButton;
    @FXML
    private Button addElementButton;
    @FXML
    private Label collectionInfo;
    @FXML
    private Label userNameField;

    @FXML
    private void initialize() {

        addElementButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Запрос на выполнение команды add");

            AddCommandForm.openAddForm();

        });
    }

    public static void openMainForm() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("table-form.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
