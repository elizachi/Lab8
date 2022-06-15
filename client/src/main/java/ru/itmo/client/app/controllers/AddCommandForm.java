package ru.itmo.client.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;

import java.io.IOException;

public class AddCommandForm {
    @FXML
    private TextField name;
    @FXML
    private TextField soundtrackName;
    @FXML
    private TextField minutesOfWaiting;
    @FXML
    private TextField impactSpeed;
    @FXML
    private RadioButton falseHero;
    @FXML
    private RadioButton trueHero;
    @FXML
    private RadioButton trueHasToothpick;
    @FXML
    private RadioButton falseHasToothpick;
    @FXML
    private TextField coordX;
    @FXML
    private TextField coordY;
    @FXML
    private ComboBox<?> mood;
    @FXML
    private TextField carName;
    @FXML
    private CheckBox carIsCool;

    @FXML
    private Button clearButton;
    @FXML
    private Button createButton;

    @FXML
    private void initialize() {

        clearButton.setOnAction(event -> {
            toDefaultSettings();
        });

        falseHero.setOnAction(event -> {
            trueHero.setSelected(false);
            falseHero.setSelected(true);
        });

        trueHero.setOnAction(event ->{
            falseHero.setSelected(false);
            trueHero.setSelected(true);
        });

        falseHasToothpick.setOnAction(event -> {
            trueHasToothpick.setSelected(false);
        });

        trueHasToothpick.setOnAction(event ->{
            falseHasToothpick.setSelected(false);
        });

    }

    public static void openAddForm() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("add-command-form.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 679, 368);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toDefaultSettings() {
        name.clear();
        soundtrackName.clear();
        minutesOfWaiting.clear();
        impactSpeed.clear();
        falseHero.setSelected(true);
        trueHero.setSelected(false);
        falseHasToothpick.setSelected(true);
        trueHasToothpick.setSelected(false);
        coordX.clear();
        coordY.clear();
        //mood
        carName.clear();
        carIsCool.disarm();
    }
}
