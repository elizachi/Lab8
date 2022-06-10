package ru.itmo.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Класс, отвечающий за окошко входа в приложение
 */
public class SignInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signInButton;

    @FXML
    private Label welcomeText;

    @FXML
    private Label welcomeText1;

    @FXML
    void initialize() {
        assert signInButton != null : "fx:id=\"SignInButton\" was not injected: check your FXML file 'authorization-form.fxml'.";
        assert welcomeText != null : "fx:id=\"welcomeText\" was not injected: check your FXML file 'authorization-form.fxml'.";
        assert welcomeText1 != null : "fx:id=\"welcomeText1\" was not injected: check your FXML file 'authorization-form.fxml'.";

        // при нажатии на кнопку "Войти"
        signInButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Attempt to log in to the app");
            signInButton.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("table-form.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 640, 480);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}