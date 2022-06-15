package ru.itmo.client.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.common.general.User;

import java.io.IOException;

public class TableFormController {

    @FXML
    private Circle userColour;
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

    private static User user;

    public static User getUser() {
        return user;
    }

    @FXML
    private void initialize() {

        userNameField.setText(user.getUsername());
        userColour.setFill(Color.valueOf(user.getColour()));
        userColour.setStroke(Color.valueOf(user.getColour()));

        addElementButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Запрос на выполнение команды add");

            AddCommandForm.openAddForm();

        });
    }

    public static void openMainForm(User currentUser) {
        user = currentUser;

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