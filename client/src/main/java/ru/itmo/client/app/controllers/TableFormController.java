package ru.itmo.client.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.controllers.AddCommandForm;
import ru.itmo.client.app.utility.Animation;
import ru.itmo.client.app.utility.ResourceController;
import ru.itmo.client.general.LanguageChanger;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;

public class TableFormController {

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
    private MenuItem searchMenuButton;
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
    private TableColumn<HumanBeing, Boolean> isCoolColumn;
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
    private Map<String, Color> userColorMap;
    private Map<Shape, Long> shapeMap;
    private Map<Long, Text> textMap;
    private Map<String, Locale> localeMap;
    private final ResourceController resourceController = new ResourceController();
    private final LanguageChanger language = new LanguageChanger(resourceController);
    Animation animation = new Animation();

    public static User getUser() {
        return user;
    }

    @FXML
    void initialize() {
        //...тут будет заполнение таблицы
        //TODO где-то загружать userColorMap

        userNameField.setText(user.getUsername());
        userColour.setFill(Color.valueOf(user.getColour()));
        userColour.setStroke(Color.valueOf(user.getColour()));

        addElementButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Запрос на выполнение команды add");

            AddCommandForm.openAddForm(resourceController);

        });

        userColorMap = new HashMap<>();
        shapeMap = new HashMap<>();
        textMap = new HashMap<>();
        localeMap = new HashMap<>();

        language.setLanguages(localeMap);
        languageChoice.setItems(FXCollections.observableArrayList(localeMap.keySet()));

        changeLanguage();
    }

    public static void openMainForm(User currentUser) {
        user = currentUser;

        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("table-form.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void refreshCanvas(){
        shapeMap.keySet().forEach(s -> canvasPane.getChildren().remove(s));
        shapeMap.clear();
        textMap.values().forEach(s -> canvasPane.getChildren().remove(s));
        textMap.clear();

        for (HumanBeing human: humanBeingTable.getItems()) {
            //TODO ну собсна... загружать хуманов
        }
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
        setProperty(nameColumn, "NameColumn");
        setProperty(soundTrackColumn, "SoundTrackColumn");
        setProperty(realHeroColumn, "RealHeroColumn");
        setProperty(toothPickColumn, "ToothPickColumn");
        setProperty(coordinatesColumn, "CoordinatesColumn");
        setProperty(xColumn, "XColumn");
        setProperty(yColumn, "YColumn");
        setProperty(moodColumn, "MoodColumn");
        setProperty(carColumn, "CarColumn");
        setProperty(carNameColumn, "CarNameColumn");
        setProperty(isCoolColumn, "IsCoolColumn");
        setProperty(userColumn, "UserColumn");
        //для кнопочек
        addElementButton.textProperty().bind(resourceController.getStringBinding("AddButton"));
        menuButton.textProperty().bind(resourceController.getStringBinding("MenuButton"));
            profileMenuButton.textProperty().bind(resourceController.getStringBinding("ProfileMenuButton"));
            helpMenuButton.textProperty().bind(resourceController.getStringBinding("HelpMenuButton"));
            searchMenuButton.textProperty().bind(resourceController.getStringBinding("SearchMenuButton"));
        settingsButton.textProperty().bind(resourceController.getStringBinding("SettingsButton"));
            switchUserSettingsButton.textProperty().bind(resourceController.getStringBinding("SwitchUserSettingsButton"));
            switchColorSettingsButton.textProperty().bind(resourceController.getStringBinding("SwitchColorSettingsButton"));
        //для полей
        collectionLabel.textProperty().bind(resourceController.getStringBinding("CollectionLabel"));
        coordinatesLabel.textProperty().bind(resourceController.getStringBinding("CoordinatesLabel"));
        collectionInfoLabel.textProperty().bind(resourceController.getStringBinding("CollectionInfoLabel"));
    }

    private void setProperty(TableColumn<?,?> column, String text){
        column.textProperty().bind(resourceController.getStringBinding(text));
    }
}
