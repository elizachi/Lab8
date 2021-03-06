package ru.itmo.client.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.exceptions.CheckHumanException;
import ru.itmo.client.app.exceptions.CommandException;
import ru.itmo.client.app.utility.CommandValidator;
import ru.itmo.client.app.utility.ResourceController;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.general.User;
import ru.itmo.common.model.Car;
import ru.itmo.common.model.Coordinates;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.model.Mood;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCommandForm {

    @FXML
    private Tooltip moodTooltip;
    @FXML
    private Tooltip carTooltip;
    @FXML
    private Tooltip coordinatesTooltip;
    @FXML
    private Tooltip toothpickTooltip;
    @FXML
    private Tooltip heroFieldTooltip;
    @FXML
    private Tooltip speedFieldTooltip;
    @FXML
    private Tooltip minutesFieldTooltip;
    @FXML
    private Tooltip soundtrackFieldTooltip;
    @FXML
    private Tooltip nameFieldTooltip;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane addAnchorPane;

    @FXML
    private CheckBox carIsCoolField;

    @FXML
    private Button clearButton;
    @FXML
    private Button updateButton;
    @FXML
    private RadioButton falseHasToothpickButton;
    @FXML
    private RadioButton falseHeroButton;
    @FXML
    private RadioButton trueHasToothpickButton;
    @FXML
    private RadioButton trueHeroButton;

    @FXML
    private ComboBox<String> moodComboBox;

    @FXML
    private MenuItem mainItem;
    @FXML
    private Label mainLabel;
    @FXML
    private ContextMenu nameContextMenu;

    @FXML
    private TextField nameField;
    @FXML
    private TextField soundtrackNameField;
    @FXML
    private TextField minutesOfWaitingField;
    @FXML
    private TextField xCooField;
    @FXML
    private TextField yCooField;
    @FXML
    private TextField impactSpeedField;
    @FXML
    private TextField carNameField;

    @FXML
    private Text textCar;
    @FXML
    private Text textCoordinates;
    @FXML
    private Text textHasToothpick;
    @FXML
    private Text textHeroStatus;

    private static User user;
    private static HumanBeing human;
    private static ResourceController resourceController;

    private static Integer id;
    private static void currentId(int id) {
        UpdateCommandForm.id = id;
    }

    public static HumanBeing getHuman() {
        return human;
    }

    public static void setHuman(HumanBeing human){
        UpdateCommandForm.human = human;
    }

    @FXML
    private void initialize() {

        CommandValidator checkValue = new CommandValidator();
        setLanguage();

        updateButton.setOnAction(event -> {
            try {
                UpdateCommandForm.human = checkValue.checkFields(CommandType.UPDATE, check(checkValue), user);
                updateButton.getScene().getWindow().hide();

                ClientAppLauncher.log.info("?????????????? update ?????????????? ??????????????????");
            } catch (CommandException | CheckHumanException e) {
                ClientAppLauncher.log.info("???? ?????????? ???????????????????? ?????????????? update ?????????????????? ????????????");
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

    public static void openUpdateForm(ResourceController resourceController, Integer id) {

        user = TableFormController.getCurrentUser();
        UpdateCommandForm.resourceController = resourceController;

        UpdateCommandForm.currentId(id);

        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("update-command-form.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("update human");
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

        HumanBeing updHuman = new HumanBeing(
                nameField.getText(),
                soundtrackNameField.getText(),
                Long.parseLong(minutesOfWaitingField.getText()),
                Integer.parseInt(impactSpeedField.getText()),
                trueHeroButton.isSelected(),
                isSelectOrNull(),
                new Coordinates(
                        Integer.parseInt(xCooField.getText()), Float.parseFloat(yCooField.getText())
                ), convertToMood(),
                new Car(carNameField.getText(), carIsCoolField.isSelected()),
                user
        );
        updHuman.setId(UpdateCommandForm.id);
        return updHuman;
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

    public void initLanguages(ResourceController resourceController) {
        UpdateCommandForm.resourceController = resourceController;
        setLanguage();
    }

    /**
     * ???????????? ???????? ????????????????????
     */
    private void setLanguage(){
        setProperty(textCar, "TextCar");
        setProperty(textCoordinates, "TextCoordinates");
        setProperty(textHasToothpick, "TextHasToothpick");
        setProperty(textHeroStatus, "TextHeroStatus");

        setProperty(nameField, "NameField");
        setProperty(carNameField, "CarNameField");
        setProperty(impactSpeedField, "ImpactSpeedField");
        setProperty(soundtrackNameField, "SoundField");
        setProperty(minutesOfWaitingField, "MinutesField");
        setProperty(xCooField, "XCooField");
        setProperty(yCooField, "yCooField");

        setProperty(clearButton, "ClearButton");
        setProperty(updateButton, "UpdButton");
        setProperty(falseHeroButton, "FalseHeroButton");
        setProperty(trueHeroButton, "TrueHeroButton");
        setProperty(falseHasToothpickButton, "FalseHasToothpickButton");
        setProperty(trueHasToothpickButton, "TrueHasToothpickButton");

        carIsCoolField.textProperty().bind(resourceController.getStringBinding("CarCoolField"));
        moodComboBox.promptTextProperty().bind(resourceController.getStringBinding("MoodComboBox"));

        nameFieldTooltip.textProperty().bind(resourceController.getStringBinding("nameLabel"));
        carTooltip.textProperty().bind(resourceController.getStringBinding("carLabel"));
        toothpickTooltip.textProperty().bind(resourceController.getStringBinding("toothLabel"));
        speedFieldTooltip.textProperty().bind(resourceController.getStringBinding("minutesLabel"));
        minutesFieldTooltip.textProperty().bind(resourceController.getStringBinding("minutesLabel"));
        moodTooltip.textProperty().bind(resourceController.getStringBinding("moodLabel"));
        soundtrackFieldTooltip.textProperty().bind(resourceController.getStringBinding("soundLabel"));
        heroFieldTooltip.textProperty().bind(resourceController.getStringBinding("heroLabel"));
        coordinatesTooltip.textProperty().bind(resourceController.getStringBinding("coordLabel"));
    }

    //sets Property for TextField
    private void setProperty(TextField field, String text){
        field.promptTextProperty().bind(resourceController.getStringBinding(text));
    }
    //sets Property for Button
    private void setProperty(Button button, String text){
        button.textProperty().bind(resourceController.getStringBinding(text));
    }
    //sets Property for Text
    private void setProperty(Text field, String text){
        field.textProperty().bind(resourceController.getStringBinding(text));
    }
    //sets Property for RadioButton
    private void setProperty(RadioButton button, String text){
        button.textProperty().bind(resourceController.getStringBinding(text));
    }
}
