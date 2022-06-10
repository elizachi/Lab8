package ru.itmo.client.auth.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField authLoginField;

    @FXML
    private TextField authPasswordField;

    @FXML
    private TextField regLoginField;

    @FXML
    private TextField regPasswordField;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signInButton;
    @FXML
    void initialize() {

        // при нажатии на кнопку "Войти"
        signInButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Attempt to log in to the app");
            signInButton.getScene().getWindow().hide();

            String login = authLoginField.getText().trim();
            String password = authPasswordField.getText().trim();

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