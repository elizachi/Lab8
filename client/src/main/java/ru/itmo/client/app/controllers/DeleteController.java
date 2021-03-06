package ru.itmo.client.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.itmo.client.ClientAppLauncher;
import ru.itmo.client.app.utility.ResourceController;
import ru.itmo.client.general.Client;
import ru.itmo.client.general.ClientLoader;
import ru.itmo.common.general.CommandType;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteController {
    @FXML
    private Button noButton;
    @FXML
    private Button yesButton;
    @FXML
    private Text areYouSureText;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    private static ResourceController resourceController;
    private static Integer id;

    private static void currentId(int id) {
        DeleteController.id = id;
    }

    @FXML
    void initialize() {
        setLanguage();

        yesButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Было выбрано удаление элемента");

            HumanBeing requestHuman = new HumanBeing();
            requestHuman.setId(id);

            Response response = new Client(ClientLoader.getServerHost(), ClientLoader.getServerPort())
                    .send(new Request(
                            CommandType.DELETE, requestHuman, TableFormController.getCurrentUser()
                    ));

            if(response.getStatus() == Response.Status.OK) {
                ClientAppLauncher.log.info("Удаление произведено успешно");
            } else {
                ClientAppLauncher.log.info("Не удалось удалить элемент");
            }

            yesButton.getScene().getWindow().hide();
        });

        noButton.setOnAction(event -> {
            ClientAppLauncher.log.info("Удаление элемента отменено");

            noButton.getScene().getWindow().hide();
        });

    }

    public static void openDeleteForm(ResourceController resourceContr, Integer id) {
        resourceController = resourceContr;

        DeleteController.currentId(id);

        FXMLLoader fxmlLoader = new FXMLLoader(ClientAppLauncher.class.getResource("delete-form.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Delete");
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLanguage(){
        areYouSureText.textProperty().bind(resourceController.getStringBinding("DeleteAreYouSureText"));
        yesButton.textProperty().bind(resourceController.getStringBinding("YesButton"));
        noButton.textProperty().bind(resourceController.getStringBinding("NoButton"));
    }
}
