package ru.itmo.client.auth.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.controllers.TableFormController;
import ru.itmo.client.auth.exceptions.AuthException;
import ru.itmo.client.auth.exceptions.CheckUserException;
import ru.itmo.client.auth.utility.AuthValidator;
import ru.itmo.client.auth.utility.GenerateColours;
import ru.itmo.common.general.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Класс, отвечающий за окошко входа в приложение
 */
public class AuthController {
    @FXML
    private Button logInButton;
    @FXML
    private TextField authLoginField;
    @FXML
    private PasswordField authPasswordField;
    @FXML
    private Text errorAuthTextField;

    @FXML
    private Button signUpButton;
    @FXML
    private TextField regLoginField;
    @FXML
    private PasswordField regPasswordField;
    @FXML
    private Circle userColour;
    @FXML
    private Text errorRegTextField;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private VBox authScene;
    @FXML
    private void initialize() {

        AuthValidator checkValue = new AuthValidator();

        loadColour();

        // при нажатии на кнопку "Войти"
        logInButton.setOnAction(event -> {

            ClientAppLauncher.log.info("Попытка войти в приложение...");

            String login = authLoginField.getText().trim();
            String password = authPasswordField.getText().trim();

            try {
                User user = checkValue.checkAuth(login, password);
                switchToApp();
                ClientAppLauncher.log.info("Попытка успешна");
            } catch (CheckUserException e) {
                ClientAppLauncher.log.info("Попытка провалена:\n" + e.getErrorType().getTitle());
                errorAuthTextField.setText(e.getErrorType().getTitle());
            } catch (AuthException e) {
                ClientAppLauncher.log.error("Ошибка авторизации:\n" + e.getErrorType().getTitle());
                errorAuthTextField.setText(e.getErrorType().getTitle());
            }

        });

        userColour.setOnMouseClicked((MouseEvent e) ->
                setNewColour(GenerateColours.generateColor()));

        signUpButton.setOnAction(event -> {

            ClientAppLauncher.log.info("Попытка зарегестрироваться...");

            String login = regLoginField.getText().trim();
            String password = regPasswordField.getText().trim();

            try {
                User user = checkValue.checkReg(login, password, userColour.getFill());
                switchToApp();
                ClientAppLauncher.log.info("Попытка успешна");
            } catch (CheckUserException e) {
                ClientAppLauncher.log.info("Попытка провалена:\n" + e.getErrorType().getTitle());
                errorRegTextField.setText(e.getErrorType().getTitle());
            } catch (AuthException e) {
                ClientAppLauncher.log.error("Ошибка авторизации:\n" + e.getErrorType().getTitle());
                errorRegTextField.setText(e.getErrorType().getTitle());
            }

        });

    }

    private void switchToApp() {
        logInButton.getScene().getWindow().hide();

        TableFormController.openMainForm();
    }

    private void loadColour() {
        Color color = GenerateColours.generateColor();
        userColour.setFill(color);
        userColour.setStroke(color);
    }

    private void setNewColour(Color colour) {
        Color color = GenerateColours.generateColor();
        userColour.setFill(color);
        userColour.setStroke(color);
    }
}