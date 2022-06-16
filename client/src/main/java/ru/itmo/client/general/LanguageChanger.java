package ru.itmo.client.general;

import javafx.scene.control.ChoiceBox;
import ru.itmo.client.app.utility.ResourceController;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LanguageChanger {
    private ResourceController resourceController;

    public LanguageChanger(ResourceController resourceController){
        this.resourceController = resourceController;
    }

    /**
     * Меняет язык приложения в зависимости от выбора
     * Но!! Чтобы изменился текст, нужно применить индивидуальный для каждого окна метод setLanguage()
     */
    public void changeLanguage(Map<String, Locale> localeMap, ChoiceBox<String> languageChoice) {
        resourceController.setResources(ResourceBundle.getBundle("bundles.gui.gui"));

        for (String localeName : localeMap.keySet()) {
            if (localeMap.get(localeName).equals(resourceController.getResources().getLocale())) {
                languageChoice.getSelectionModel().select(localeName);
            }
        }
        if (languageChoice.getSelectionModel().getSelectedItem().isEmpty())
            languageChoice.getSelectionModel().selectFirst();

        languageChoice.setOnAction(event -> {
            resourceController.setResources(ResourceBundle.getBundle("bundles.gui.gui",
                    localeMap.get(languageChoice.getValue())));
        });
    }

    public void setLanguages(Map<String, Locale> localeMap){
        localeMap.put("RU", new Locale("ru", "RU"));
        localeMap.put("EN", new Locale("en", "US"));
        localeMap.put("PT", new Locale("pt", "PT"));
        localeMap.put("IT", new Locale("it", "IT"));
    }
}
