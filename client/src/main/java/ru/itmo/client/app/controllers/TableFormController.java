package ru.itmo.client.app.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.utility.LoadData;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Deque;

import static ru.itmo.common.model.Mood.GLOOM;

public class TableFormController {
    private ObservableList<HumanBeing> listOfHumans = FXCollections.observableArrayList();
    @FXML
    private TableView<HumanBeing> humanBeingTable = new TableView<>(listOfHumans);
    @FXML
    private TableColumn<HumanBeing, Integer> idColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, LocalDate> creationDateColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, String> nameColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, String> soundTrackColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Long> minutesColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Integer> impactSpeedColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Boolean> realHeroColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Boolean> toothpickColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Coordinates> coordinatesColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Integer> xCooColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Float> yCooColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, String> moodColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Car> carColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, String> carNameColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Boolean> carCoolColumn = new TableColumn<>();
    @FXML
    private TableColumn<HumanBeing, Color> userColorColumn = new TableColumn<>();

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

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    @FXML
    private void initialize() {

        initForm();

        initTable();

        addElementButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Запрос на выполнение команды add");

            AddCommandForm.openAddForm();

        });
    }

    public static void openMainForm(User user) {
        currentUser = user;

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

    private void initTable() {

        // TODO здесь функция выгрузки массива хуманов из бд

        listOfHumans.addAll(new LoadData().load());

        idColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId())
        );
        creationDateColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate())
        );
        nameColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName())
        );
        soundTrackColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSoundtrackName())
        );
        minutesColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMinutesOfWaiting())
        );
        impactSpeedColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getImpactSpeed())
        );
        realHeroColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().isRealHero())
        );
        toothpickColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().isHasToothpick())
        );
        xCooColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX())
        );
        yCooColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY())
        );

        moodColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(
                        cellData.getValue().getMood() == null ? null : cellData.getValue().getMood().name()
                )
        );
        carNameColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCar().getCarName())
        );
        carCoolColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCar().getCarCool())
        );

        listOfHumans.add(new HumanBeing(
                "jvjkd", "jfvjkd", 12L, 13,
                true, true, new Coordinates(2, 3.0F), GLOOM, new Car("vk", true)));

        humanBeingTable.setItems(listOfHumans);
    }

    private void initForm() {
        userNameField.setText(currentUser.getUsername());
        userColour.setFill(Color.valueOf(currentUser.getColour()));
        userColour.setStroke(Color.valueOf(currentUser.getColour()));
    }
}
