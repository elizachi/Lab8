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
import ru.itmo.client.app.utility.CommandValidator;
import ru.itmo.common.general.CommandType;

import java.io.IOException;

public class AddCommandForm {

    @FXML
    private AnchorPane addAnchorPane;
    @FXML
    private TextField nameField;
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

    @FXML
    private void initialize() {

        CommandValidator checkValue = new CommandValidator();

        createButton.setOnAction(event ->{

            try {
                checkValue.checkFields(
                        nameField.getText(), soundtrackNameField.getText(), minutesOfWaitingField.getText(),
                        impactSpeedField.getText(), trueHeroButton.isSelected(), isSelectOrNull(), xCooField.getText(),
                        yCooField.getText(), moodComboBox.getSelectionModel().getSelectedItem(), carNameField.getText(),
                        carIsCoolField.isSelected(), CommandType.ADD
                );
            } catch (CheckHumanException e) {
                throw new RuntimeException(e);
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
}
