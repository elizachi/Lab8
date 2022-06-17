package ru.itmo.client.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.utility.ResourceController;

public class HelpController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private AnchorPane helpAnchorPane;
    @FXML
    private Label helpLabel;

    private static ResourceController resourceController;

    @FXML
    void initialize() {
        helpLabel.textProperty().bind(resourceController.getStringBinding("HelpLabel"));
    }

    public static void openForm(ResourceController resourceContr) {
        resourceController = resourceContr;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("help-form.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Help");
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
