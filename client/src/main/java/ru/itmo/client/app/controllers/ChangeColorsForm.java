package ru.itmo.client.app.controllers;

import java.io.IOException;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.utility.ResourceController;

public class ChangeColorsForm {

    @FXML
    private AnchorPane imgAnchorPane;
    @FXML
    private ImageView imgPicture;
    @FXML
    private Label textOnImg;

    private static ResourceController resourceController = new ResourceController();
    private final Image imageRU = new Image("images/noRU.jpg");
    private final Image imageEN = new Image("images/noEN.jpg");
    private final Image imageIT = new Image("images/noIT.jpg");
    private final Image imagePT = new Image("images/noPT.jpg");
    private Image image;

    @FXML
    private void initialize() {
        Locale locale = resourceController.getResources().getLocale();

        if (new Locale("en", "US").equals(locale)) {
            image = imageEN;
        } else if (new Locale("it", "IT").equals(locale)){
            image = imageIT;
        } else if (new Locale("pt", "PT").equals(locale)){
            image = imagePT;
        } else {
            image = imageRU;
        }

        imgPicture.setImage(image);
        imgAnchorPane.setMaxSize(image.getWidth(), image.getHeight());
        textOnImg.textProperty().bind(resourceController.getStringBinding("TextOnImg"));
    }

    public static void openForm(ResourceController resourceContr) {
        resourceController = resourceContr;
        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("change-color-form.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setResizable(false);
            stage.setTitle("No!");
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
