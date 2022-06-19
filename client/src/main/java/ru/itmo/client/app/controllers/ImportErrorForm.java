package ru.itmo.client.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.utility.ResourceController;

public class ImportErrorForm {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private Label errorLabel;
    @FXML
    private Label errorMessage;

    private static ResourceController resourceController;

    @FXML
    void initialize() {
        errorMessage.textProperty().bind(resourceController.getStringBinding("errorMessage"));
        errorLabel.textProperty().bind(resourceController.getStringBinding("errorLabel"));
    }

    public static void openForm(ResourceController resourceContr) {
        resourceController = resourceContr;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("import-error-form.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("ERROR!");
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
