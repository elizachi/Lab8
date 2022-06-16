package ru.itmo.client.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;

import java.io.IOException;
import java.time.LocalDate;

public class TableFormController {
    private final ObservableList<HumanBeing> humans = FXCollections.observableArrayList();
    @FXML
    private TableView<HumanBeing> humanBeingTable;
    @FXML
    private TableColumn<HumanBeing, Integer> idColumn;
    @FXML
    private TableColumn<HumanBeing, LocalDate> creationDateColumn;
    @FXML
    private TableColumn<HumanBeing, String> nameColumn;
    @FXML
    private TableColumn<HumanBeing, String> soundTrackColumn;
    @FXML
    private TableColumn<HumanBeing, Long> minutesColumn;
    @FXML
    private TableColumn<HumanBeing, Integer> impactSpeedColumn;
    @FXML
    private TableColumn<HumanBeing, Boolean> realHeroColumn;
    @FXML
    private TableColumn<HumanBeing, Boolean> toothpickColumn;
    @FXML
    private TableColumn<HumanBeing, Coordinates> coordinatesColumn;
    @FXML
    private TableColumn<HumanBeing, Integer> xCooColumn;
    @FXML
    private TableColumn<HumanBeing, Float> yCooColumn;
    @FXML
    private TableColumn<HumanBeing, Mood> moodColumn;
    @FXML
    private TableColumn<HumanBeing, Car> carColumn;
    @FXML
    private TableColumn<HumanBeing, String> carNameColumn;
    @FXML
    private TableColumn<HumanBeing, Boolean> carCoolColumn;
    @FXML
    private TableColumn<HumanBeing, Color> userColorColumn;

    @FXML
    private Circle userColour;
    @FXML
    private AnchorPane mainWindow;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuButton settingsButton;
    @FXML
    private Button addElementButton;
    @FXML
    private Label collectionInfo;
    @FXML
    private Label userNameField;

    private static User user;

    public static User getUser() {
        return user;
    }

    @FXML
    private void initialize() {

        userNameField.setText(user.getUsername());
        userColour.setFill(Color.valueOf(user.getColour()));
        userColour.setStroke(Color.valueOf(user.getColour()));

        addElementButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Запрос на выполнение команды add");

            AddCommandForm.openAddForm();

            humans.add(AddCommandForm.getHuman());

            humanBeingTable.setItems(humans);

        });
    }

    public static void openMainForm(User currentUser) {
        user = currentUser;

        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("table-form.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
