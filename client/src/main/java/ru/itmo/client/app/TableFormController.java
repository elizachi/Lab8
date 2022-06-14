package ru.itmo.client.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;

import java.io.IOException;

public class TableFormController {

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

    void initialize() {
        addElementButton.setOnAction(event -> {
            //add command
        });
    }

    public static void openForm() {
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
