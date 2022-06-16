package ru.itmo.client.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.exceptions.CheckHumanException;
import ru.itmo.client.app.exceptions.CommandException;
import ru.itmo.client.app.utility.CommandValidator;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;

import java.io.IOException;

public class AddCommandForm {
    @FXML
    private MenuItem mainItem;
    @FXML
    private Label mainLabel;
    @FXML
    private ContextMenu nameContextMenu;
    @FXML
    private AnchorPane addAnchorPane;
    @FXML
    private TextField nameField;
    @FXML
    private Tooltip nameTooltip;
    @FXML
    private TextField soundtrackNameField;
    @FXML
    private TextField minutesOfWaitingField;
    @FXML
    private TextField impactSpeedField;
    @FXML
    private RadioButton falseHeroButton;
    @FXML
    private RadioButton trueHeroButton;
    @FXML
    private RadioButton trueHasToothpickButton;
    @FXML
    private RadioButton falseHasToothpickButton;
    @FXML
    private TextField xCooField;
    @FXML
    private TextField yCooField;
    @FXML
    private ComboBox<String> moodComboBox = new ComboBox<String>();
    @FXML
    private TextField carNameField;
    @FXML
    private CheckBox carIsCoolField;

    @FXML
    private Button clearButton;
    @FXML
    private Button createButton;

    private static User user;

    private static HumanBeing human;

    public static HumanBeing getHuman() {
        return human;
    }

    @FXML
    private void initialize() {

        CommandValidator checkValue = new CommandValidator();

        createButton.setOnAction(event -> {
            try {
                human = checkValue.checkFields(CommandType.ADD, check(checkValue), user);

                createButton.getScene().getWindow().hide();

                ClientAppLauncher.log.info("Команда add успешно выполнена");
            } catch (CommandException | CheckHumanException e) {
                ClientAppLauncher.log.info("Во время выполнения команды add произошла ошибка");
            }
        });


        moodComboBox.getItems().addAll("", "sadness", "longing", "gloom", "rage");

        clearButton.setOnAction(event -> {
            toDefaultSettings();
        });

        falseHeroButton.setOnAction(event -> {
            trueHeroButton.setSelected(false);
            falseHeroButton.setSelected(true);
        });

        trueHeroButton.setOnAction(event ->{
            falseHeroButton.setSelected(false);
            trueHeroButton.setSelected(true);
        });

        falseHasToothpickButton.setOnAction(event -> {
            trueHasToothpickButton.setSelected(false);
        });

        trueHasToothpickButton.setOnAction(event ->{
            falseHasToothpickButton.setSelected(false);
        });



    }

    public static void openAddForm() {

        user = TableFormController.getCurrentUser();

        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("add-command-form.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toDefaultSettings() {
        nameField.clear();
        soundtrackNameField.clear();
        minutesOfWaitingField.clear();
        impactSpeedField.clear();
        falseHeroButton.setSelected(true);
        trueHeroButton.setSelected(false);
        falseHasToothpickButton.setSelected(true);
        trueHasToothpickButton.setSelected(false);
        xCooField.clear();
        yCooField.clear();
        moodComboBox.getSelectionModel().select(0);
        carNameField.clear();
        carIsCoolField.disarm();
    }

    private Boolean isSelectOrNull() {
        if(trueHasToothpickButton.isSelected()) {
            return true;
        } else if(falseHasToothpickButton.isSelected()) {
            return false;
        } else {
            return null;
        }
    }

    private HumanBeing check(CommandValidator valid) throws CheckHumanException {
        try {
            valid.checkNonNullFields(nameField.getText());
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            nameField.setStyle("-fx-border-color: #fc6666;");
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            valid.checkNonNullFields(soundtrackNameField.getText());
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            soundtrackNameField.setStyle("-fx-border-color: #fc6666;");
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            valid.checkLongFields(minutesOfWaitingField.getText());
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            minutesOfWaitingField.setStyle("-fx-border-color: #fc6666;");
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            valid.checkIntFields(impactSpeedField.getText());
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            impactSpeedField.setStyle("-fx-border-color: #fc6666;");
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            valid.checkLimitedInt(xCooField.getText());
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            xCooField.setStyle("-fx-border-color: #fc6666;");
            throw new CheckHumanException(e.getErrorType());
        }

        try {
            valid.checkLimitedFloat(yCooField.getText());
        } catch (CheckHumanException e) {
            ClientAppLauncher.log.error(e.getErrorType().getTitle());
            yCooField.setStyle("-fx-border-color: #fc6666;");
            throw new CheckHumanException(e.getErrorType());
        }

        return new HumanBeing(
                nameField.getText(),
                soundtrackNameField.getText(),
                Long.parseLong(minutesOfWaitingField.getText()),
                Integer.parseInt(impactSpeedField.getText()),
                trueHeroButton.isSelected(),
                isSelectOrNull(),
                new Coordinates(
                        Integer.parseInt(xCooField.getText()), Float.parseFloat(yCooField.getText())
                ), convertToMood(),
                new Car(carNameField.getText(), carIsCoolField.isSelected())
        );
    }

    private Mood convertToMood() {
        if(moodComboBox.getSelectionModel().getSelectedItem() == null) {
            return null;
        } else if(moodComboBox.getSelectionModel().getSelectedItem().isEmpty()) {
            return null;
        } else {
            return Mood.valueOf(moodComboBox.getSelectionModel().getSelectedItem().toUpperCase());
        }
    }
}
