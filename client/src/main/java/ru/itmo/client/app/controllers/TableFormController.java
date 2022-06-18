package ru.itmo.client.app.controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.utility.Animation;
import ru.itmo.client.app.utility.ResourceController;
import ru.itmo.client.app.utility.LoadData;
import ru.itmo.client.general.LanguageChanger;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;


public class TableFormController {
    @FXML
    private ChoiceBox<String> choiceSearch;
    @FXML
    private MenuItem idFilter;
    @FXML
    private MenuItem nameFilter;
    @FXML
    private MenuItem minutesFilter;
    @FXML
    private TextField filterValue;
    @FXML
    private Button clearButton;

    private final ObservableList<HumanBeing> listOfHumans = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private Circle userColour;
    @FXML
    private AnchorPane mainWindow;
    @FXML
    private AnchorPane canvasPane;

    @FXML
    private Label userNameField;
    @FXML
    private Label collectionInfo;
    @FXML
    private Text collectionLabel;
    @FXML
    private Text collectionInfoLabel;
    @FXML
    private Text coordinatesLabel;

    @FXML
    private Button addElementButton;
    @FXML
    private MenuItem helpMenuButton;
    @FXML
    private MenuItem profileMenuButton;
    @FXML
    private MenuItem switchColorSettingsButton;
    @FXML
    private MenuItem switchUserSettingsButton;
    @FXML
    private ChoiceBox<String> languageChoice;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuButton settingsButton;

    @FXML
    private TableView<HumanBeing> humanBeingTable;
    @FXML
    private TableColumn<HumanBeing, Car> carColumn;
    @FXML
    private TableColumn<HumanBeing, String> carNameColumn;
    @FXML
    private TableColumn<HumanBeing, Coordinates> coordinatesColumn;
    @FXML
    private TableColumn<HumanBeing, Integer> idColumn;
    @FXML
    private TableColumn<HumanBeing, LocalDate> creationDateColumn;
    @FXML
    private TableColumn<HumanBeing, Long> minutesColumn;
    @FXML
    private TableColumn<HumanBeing, Integer> impactSpeedColumn;
    @FXML
    private TableColumn<HumanBeing, Boolean> carCoolColumn;
    @FXML
    private TableColumn<HumanBeing, Mood> moodColumn;
    @FXML
    private TableColumn<HumanBeing, String> nameColumn;
    @FXML
    private TableColumn<HumanBeing, Boolean> realHeroColumn;
    @FXML
    private TableColumn<HumanBeing, String> soundTrackColumn;
    @FXML
    private TableColumn<HumanBeing, Boolean> toothPickColumn;
    @FXML
    private TableColumn<HumanBeing, String> userColumn;
    @FXML
    private TableColumn<HumanBeing, Integer> xColumn;
    @FXML
    private TableColumn<HumanBeing, Float> yColumn;

    private static User user;
    private Map<Shape, Integer> shapeMap;
    private Map<Integer, Text> textMap;
    private Map<String, Locale> localeMap;
    private final ResourceController resourceController = new ResourceController();
    private final LanguageChanger language = new LanguageChanger(resourceController);
    Animation animation = new Animation();

    public static User getCurrentUser() {
        return user;
    }

    @FXML
    private void initialize() {
        initializeTable();

        filter();

        userNameField.setText(user.getUsername());
        userColour.setFill(Color.valueOf(user.getColour()));
        userColour.setStroke(Color.valueOf(user.getColour()));

        addElementButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Запрос на выполнение команды add");

            try {
                AddCommandForm.openAddForm(resourceController);
                listOfHumans.add(AddCommandForm.getHuman());
            } catch (RuntimeException ignored) {}

            ClientAppLauncher.log.info("Форма добавления элемента была закрыта");

            humanBeingTable.setItems(FXCollections.observableArrayList(listOfHumans));
            humanBeingTable.getSelectionModel().clearSelection();
            refreshCanvas();
        });

        clearButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Запрос на выполнение команды clear");
            try {
                ClearController.openClearForm(resourceController);
            } catch (RuntimeException ignored) {}

            refreshCanvas();
        });

        switchColorSettingsButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Запрос на смену цвета");
            ChangeColorsForm.openForm(resourceController);
        });

        switchUserSettingsButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Смена пользователя");
            //TODO переключение на регистрацию
        });

        helpMenuButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Открытие окошка помощи...");
            HelpController.openForm(resourceController);
        });

        shapeMap = new HashMap<>();
        textMap = new HashMap<>();
        localeMap = new HashMap<>();

        refreshCanvas();

        language.setLanguages(localeMap);
        languageChoice.setItems(FXCollections.observableArrayList(localeMap.keySet()));

        changeLanguage();

        Thread thread = new Thread(() -> {

            Runnable update = this::initializeTable;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

                Platform.runLater(update);
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    public static void openMainForm(User currentUser) {
        user = currentUser;

        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("table-form.fxml"));
        try {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

            Scene scene = new Scene(fxmlLoader.load(), width, height);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize table.
     */
    private void initializeTable(){

        listOfHumans.clear();

        loadTable(new LoadData().load());

        idColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        creationDateColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate()));
        minutesColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getMinutesOfWaiting()));
        impactSpeedColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getImpactSpeed()));
        nameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        soundTrackColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getSoundtrackName()));
        realHeroColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().isRealHero()));
        toothPickColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().isHasToothpick()));
        xColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
        moodColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getMood()));
        carNameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCar().getCarName()));
        carCoolColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCar().getCarCool()));
        userColumn.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getUsername())
        );

        humanBeingTable.setItems(listOfHumans);

        initializeRows();

        filter();

        if (shapeMap != null) {
            refreshCanvas();
        }
    }

    private void refreshCanvas() {
        shapeMap.keySet().forEach(s -> canvasPane.getChildren().remove(s));
        shapeMap.clear();
        textMap.values().forEach(s -> canvasPane.getChildren().remove(s));
        textMap.clear();

        for (HumanBeing human : humanBeingTable.getItems()) {
            if (human != null) {
                Map<Shape, Integer> tempShapeMap = new HashMap<>();

                //создание фигур
                Shape leftHair = animation.setLeftHair();
                Shape rightHair = animation.setRightHair();
                Shape frontHair = animation.setFrontHair();
                Shape leftCheek = animation.setLeftCheek();
                Shape rightCheek = animation.setRightCheek();
                Shape leftEye = animation.setLeftEye();
                Shape rightEye = animation.setRightEye();
                Shape head = animation.setHead();
                Shape neck = animation.setNeck();
                Shape body = animation.setBody(Color.web(human.getUser().getColour()));
                Shape leftHand = animation.setLeftHand();
                Shape rightHand = animation.setRightHand();
                Shape leftLeg = animation.setLeftLeg();
                Shape rightLeg = animation.setRightLeg();
                Shape leftBoot = animation.setLeftBoot();
                Shape rightBoot = animation.setRightBoot();

                //если он настоящий герой
                if (human.isRealHero()) {
                    Shape heroCloak = animation.setHeroCloak();
                    setCoordinatesOnCanvas(heroCloak, human);
                    canvasPane.getChildren().add(heroCloak);
                    shapeMap.put(heroCloak, human.getId());
                    tempShapeMap.put(heroCloak, human.getId());
                }


                //создание текста для фигурки
                Text text = new Text("id = " + human.getId());
                text.setOnMouseClicked(body::fireEvent);
                text.setFont(Font.font(12));
                text.setFill(Color.web(human.getUser().getColour()));
                textMap.put(human.getId(), text);

                //задание координат
                setCoordinatesOnCanvas(head, human);
                setCoordinatesOnCanvas(leftHair, human);
                setCoordinatesOnCanvas(rightHair, human);
                setCoordinatesOnCanvas(frontHair, human);
                setCoordinatesOnCanvas(leftCheek, human);
                setCoordinatesOnCanvas(rightCheek, human);
                setCoordinatesOnCanvas(leftEye, human);
                setCoordinatesOnCanvas(rightEye, human);
                setCoordinatesOnCanvas(neck, human);
                setCoordinatesOnCanvas(body, human);
                setCoordinatesOnCanvas(leftHand, human);
                setCoordinatesOnCanvas(rightHand, human);
                setCoordinatesOnCanvas(leftLeg, human);
                setCoordinatesOnCanvas(rightLeg, human);
                setCoordinatesOnCanvas(leftBoot, human);
                setCoordinatesOnCanvas(rightBoot, human);
                //координаты для текста
                text.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(human.getCoordinates().getX() - 20));
                text.translateYProperty().bind(canvasPane.heightProperty().divide(2).add(human.getCoordinates().getY() - 70));

                //добавление к координатной плоскости
                canvasPane.getChildren().addAll(head, leftHair, rightHair, frontHair, leftCheek, rightCheek, leftEye, rightEye,
                        neck, body, leftHand, rightHand, leftLeg, rightLeg, leftBoot, rightBoot);
                canvasPane.getChildren().add(text);

                shapeMap.put(body, human.getId());
                shapeMap.put(leftHair, human.getId());
                shapeMap.put(rightHair, human.getId());
                shapeMap.put(frontHair, human.getId());
                shapeMap.put(leftCheek, human.getId());
                shapeMap.put(rightCheek, human.getId());
                shapeMap.put(leftEye, human.getId());
                shapeMap.put(rightEye, human.getId());
                shapeMap.put(head, human.getId());
                shapeMap.put(neck, human.getId());
                shapeMap.put(leftHand, human.getId());
                shapeMap.put(rightHand, human.getId());
                shapeMap.put(leftLeg, human.getId());
                shapeMap.put(rightLeg, human.getId());
                shapeMap.put(leftBoot, human.getId());
                shapeMap.put(rightBoot, human.getId());

                //добавление ко временной коллекции
                tempShapeMap.put(body, 0);
                tempShapeMap.put(leftHair, 1);
                tempShapeMap.put(rightHair, 2);
                tempShapeMap.put(frontHair, 3);
                tempShapeMap.put(leftCheek, 4);
                tempShapeMap.put(rightCheek, 5);
                tempShapeMap.put(leftEye, 6);
                tempShapeMap.put(rightEye, 7);
                tempShapeMap.put(head, 8);
                tempShapeMap.put(neck, 9);
                tempShapeMap.put(leftHand, 10);
                tempShapeMap.put(rightHand, 11);
                tempShapeMap.put(leftLeg, 12);
                tempShapeMap.put(rightLeg, 13);
                tempShapeMap.put(leftBoot, 14);
                tempShapeMap.put(rightBoot, 15);

                //чтобы на фигурку можно было кликнуть
                for (Shape shape : tempShapeMap.keySet()) {
                    shape.setOnMouseClicked(this::shapeOnMouseClicked);
                }

                //TODO анимация (в процессе)
            }
        }
    }

    private void setCoordinatesOnCanvas(Shape figure, HumanBeing human){
        figure.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(human.getCoordinates().getX() - 20));
        figure.translateYProperty().bind(canvasPane.heightProperty().divide(2).add(human.getCoordinates().getY() - 70));
    }

    private void shapeOnMouseClicked(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        int id = shapeMap.get(shape);
        try {
        for (HumanBeing human: humanBeingTable.getItems()) {
            if (human.getId() == id) {
                humanBeingTable.getSelectionModel().select(human);
            }
            break;
        }
            for (Shape shapes : shapeMap.keySet()) {
                if (shapeMap.get(shapes) == id) {
                    shapes.toFront();
                }
            }
        } catch (RuntimeException ignored){}
    }

    private void filter() {

        choiceSearch = new ChoiceBox<>();
        // TODO и тут локализацию нада захуярить
        choiceSearch.getItems().addAll("id", "name", "minutes");

        FilteredList<HumanBeing> filteredData = new FilteredList<>(listOfHumans, b -> true);
        filterValue.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(humanPropertySearch -> {

                String nowFilter = choiceSearch.getValue();
                if(nowFilter == null) {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lower = newValue.toLowerCase();

                    if(String.valueOf(humanPropertySearch.getId()).indexOf(lower) != -1) {
                        return true;
                    } else if(humanPropertySearch.getName().indexOf(lower) != -1) {
                        return true;
                    } else if(humanPropertySearch.getSoundtrackName().indexOf(lower) != -1) {
                        return true;
                    } else if(String.valueOf(humanPropertySearch.getMinutesOfWaiting()).indexOf(lower) != -1) {
                        return true;
                    } else if(String.valueOf(humanPropertySearch.getImpactSpeed()).indexOf(lower) != -1) {
                        return true;
                    } else if(String.valueOf(humanPropertySearch.getCoordinates().getX()).indexOf(lower) != -1) {
                        return true;
                    } else if(String.valueOf(humanPropertySearch.getCoordinates().getY()).indexOf(lower) != -1) {
                        return true;
                    } else if(String.valueOf(humanPropertySearch.getMood()).indexOf(lower) != -1) {
                        return true;
                    } else if(String.valueOf(humanPropertySearch.getCar().getCarName()).indexOf(lower) != -1) {
                        return true;
                    } else if(String.valueOf(humanPropertySearch.getCar().getCarCool()).indexOf(lower) != -1) {
                        return true;
                    } else if(humanPropertySearch.getUser().getUsername().indexOf(lower) != -1){
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            });
        });
        SortedList<HumanBeing> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(humanBeingTable.comparatorProperty());

        humanBeingTable.setItems(sortedData);
    }

    /**
     * Меняет язык приложения
     */
    private void changeLanguage(){
        language.changeLanguage(localeMap, languageChoice);
        setLanguage();
    }

    // Меняет весь текст в зависимости от выбранного языка
    private void setLanguage(){
        resourceController.setResources(ResourceBundle.getBundle(("bundles.gui.gui"),
                localeMap.get(languageChoice.getSelectionModel().getSelectedItem())));

        //для таблицы
        setProperty(idColumn, "IdColumn");
        setProperty(creationDateColumn, "CreationDateColumn");
        setProperty(nameColumn, "NameColumn");
        setProperty(minutesColumn, "MinutesColumn");
        setProperty(impactSpeedColumn, "SpeedColumn");
        setProperty(soundTrackColumn, "SoundTrackColumn");
        setProperty(realHeroColumn, "RealHeroColumn");
        setProperty(toothPickColumn, "ToothPickColumn");
        setProperty(coordinatesColumn, "CoordinatesColumn");
        setProperty(xColumn, "XColumn");
        setProperty(yColumn, "YColumn");
        setProperty(moodColumn, "MoodColumn");
        setProperty(carColumn, "CarColumn");
        setProperty(carNameColumn, "CarNameColumn");
        setProperty(carCoolColumn, "IsCoolColumn");
        setProperty(userColumn, "UserColumn");
        //для кнопочек
        addElementButton.textProperty().bind(resourceController.getStringBinding("AddButton"));
        clearButton.textProperty().bind(resourceController.getStringBinding("MainClearButton"));
        menuButton.textProperty().bind(resourceController.getStringBinding("MenuButton"));
            profileMenuButton.textProperty().bind(resourceController.getStringBinding("ProfileMenuButton"));
            helpMenuButton.textProperty().bind(resourceController.getStringBinding("HelpMenuButton"));
        settingsButton.textProperty().bind(resourceController.getStringBinding("SettingsButton"));
            switchUserSettingsButton.textProperty().bind(resourceController.getStringBinding("SwitchUserSettingsButton"));
            switchColorSettingsButton.textProperty().bind(resourceController.getStringBinding("SwitchColorSettingsButton"));
        choiceSearch.accessibleTextProperty().bind(resourceController.getStringBinding("ChoiceSearch"));
        //для полей
        collectionLabel.textProperty().bind(resourceController.getStringBinding("CollectionLabel"));
        coordinatesLabel.textProperty().bind(resourceController.getStringBinding("CoordinatesLabel"));
        collectionInfoLabel.textProperty().bind(resourceController.getStringBinding("CollectionInfoLabel"));
        collectionInfo.textProperty().setValue(resourceController.tryResource("CollectionInfo", String.valueOf(listOfHumans.size())));
    }

    private void setProperty(TableColumn<?,?> column, String text){
        column.textProperty().bind(resourceController.getStringBinding(text));
    }

    private void loadTable(Deque<HumanBeing> humans) {
        if(humans != null) {
            listOfHumans.addAll(humans);
        }
    }

    private void initializeRows() {
        humanBeingTable.setRowFactory(tv -> {
            TableRow<HumanBeing> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                HumanBeing rowData = row.getItem();
                if(rowData != null && Objects.equals(rowData.getUser().getUsername(), user.getUsername())) {
                    System.out.println(rowData.toString());
                    ContextMenu twoCommands = new ContextMenu();

                    MenuItem update = new MenuItem();
                    update.textProperty().bind(resourceController.getStringBinding("UpdateButton"));
                    MenuItem delete = new MenuItem();
                    delete.textProperty().bind(resourceController.getStringBinding("DeleteButton"));

                    twoCommands.getItems().addAll(update, delete);

                    twoCommands.show(row, event.getScreenX(), event.getScreenY());

                    update.setOnAction(updateEvent -> {
                        ClientAppLauncher.log.info("Запрос на выполнение команды update");

                        UpdateCommandForm.openUpdateForm(resourceController, rowData.getId());

                        try {
                            HumanBeing updatedHuman = UpdateCommandForm.getHuman();
                            if(updatedHuman != null) {
                                row.getItem().getCoordinates().setX(updatedHuman.getCoordinates().getX());
                                row.getItem().getCoordinates().setY(updatedHuman.getCoordinates().getY());
                                refreshCanvas();
                            }
                        } catch (RuntimeException ignored){}

                    });

                    delete.setOnAction(deleteEvent -> {
                        ClientAppLauncher.log.info("Запрос на выполнение команлы delete");

                        try {
                            DeleteController.openDeleteForm(resourceController, rowData.getId());
                        } catch (RuntimeException ignored){}
                        refreshCanvas();
                    });
                }
            });
            return row;
        });
    }
}
