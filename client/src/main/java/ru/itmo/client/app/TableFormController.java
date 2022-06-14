package ru.itmo.client.app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

public class TableFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addElementButton;

    @FXML
    private Label collectionInfo;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuButton settingsButton;

    @FXML
    private Label userNameField;

    @FXML
    void initialize() {
        addElementButton.setOnAction(event -> {
            //add command
        });
    }

}
