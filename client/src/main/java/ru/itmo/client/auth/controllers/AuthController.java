package ru.itmo.client.auth.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.controllers.TableFormController;
import ru.itmo.client.app.utility.ResourceController;
import ru.itmo.client.auth.exceptions.AuthException;
import ru.itmo.client.auth.exceptions.CheckUserException;
import ru.itmo.client.auth.utility.AuthValidator;
import ru.itmo.client.auth.utility.GenerateColours;
import ru.itmo.client.general.LanguageChanger;
import ru.itmo.common.general.User;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Класс, отвечающий за окошко входа в приложение
 */
public class AuthController {
    @FXML
    private Tab AuthChoice;
    @FXML
    private Tab RegChoice;
    @FXML
    private Button logInButton;
    @FXML
    private TextField authLoginField;
    @FXML
    private PasswordField authPasswordField;
    @FXML
    private Text errorAuthTextField;
    @FXML
    private Tooltip authTip;
    @FXML
    private Tooltip regTip;
    @FXML
    private ChoiceBox<String> languageButtonAuth;
    @FXML
    private ChoiceBox<String> languageButtonReg;

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

    private Map<String, Locale> localeMap;
    private final ResourceController resourceController = new ResourceController();
    private final LanguageChanger languageChanger = new LanguageChanger(resourceController);

    @FXML
    private void initialize() {

        AuthValidator checkValue = new AuthValidator();

        loadColour();

        localeMap = new HashMap<>();
        languageChanger.setLanguages(localeMap);
        languageButtonAuth.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        languageButtonReg.setItems(FXCollections.observableArrayList(localeMap.keySet()));

        changeLanguage();

        // при нажатии на кнопку "Войти"
        logInButton.setOnAction(event -> {

            ClientAppLauncher.log.info("Попытка войти в приложение...");

            String login = authLoginField.getText().trim();
            String password = authPasswordField.getText().trim();

            try {
                User user = checkValue.checkAuth(login, password);
                switchToApp(user);
                ClientAppLauncher.log.info("Попытка успешна");
            } catch (CheckUserException e) {
                ClientAppLauncher.log.info("Попытка провалена:\n" + e.getErrorType().getTitle());
                errorAuthTextField.setText("Ну охуеть теперь");
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
                switchToApp(user);
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

    private void changeLanguage(){
        languageChanger.changeLanguage(localeMap, languageButtonAuth);
        languageChanger.changeLanguage(localeMap, languageButtonReg);
        setLanguage();
    }

    private void setLanguage(){
        resourceController.setResources(ResourceBundle.getBundle(("bundles.gui.gui"),
                localeMap.get(languageButtonAuth.getSelectionModel().getSelectedItem())));

        //кнопочки
        signUpButton.textProperty().bind(resourceController.getStringBinding("SignUpButton"));
        AuthChoice.textProperty().bind(resourceController.getStringBinding("AuthChoice"));
        RegChoice.textProperty().bind(resourceController.getStringBinding("RegChoice"));
        logInButton.textProperty().bind(resourceController.getStringBinding("LogInButton"));

        //поля
        authTip.textProperty().bind(resourceController.getStringBinding("AuthTip"));
        authLoginField.promptTextProperty().bind(resourceController.getStringBinding("AuthLoginField"));
        authPasswordField.promptTextProperty().bind(resourceController.getStringBinding("AuthPasswordField"));
        regTip.textProperty().bind(resourceController.getStringBinding("RegTip"));
        regLoginField.promptTextProperty().bind(resourceController.getStringBinding("RegLoginField"));
        regPasswordField.promptTextProperty().bind(resourceController.getStringBinding("RegPasswordField"));
    }

    private void switchToApp(User user) {
        logInButton.getScene().getWindow().hide();
        TableFormController.openMainForm(user);
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