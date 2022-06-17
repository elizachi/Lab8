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
     * Меняет язык приложения в зависимости от выбора на кнопочке
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

    /**
     * Метод для двух кнопок
     */
    public void changeLanguage(Map<String, Locale> localeMap, ChoiceBox<String> languageChoice1, ChoiceBox<String> languageChoice2) {
        resourceController.setResources(ResourceBundle.getBundle("bundles.gui.gui"));

        for (String localeName : localeMap.keySet()) {
            if (localeMap.get(localeName).equals(resourceController.getResources().getLocale())) {
                languageChoice1.getSelectionModel().select(localeName);
                languageChoice2.getSelectionModel().select(localeName);
            }
        }
        if (languageChoice1.getSelectionModel().getSelectedItem().isEmpty()) {
            languageChoice1.getSelectionModel().selectFirst();
            languageChoice2.getSelectionModel().selectFirst();
        }

        languageChoice1.setOnAction(event -> {
            resourceController.setResources(ResourceBundle.getBundle("bundles.gui.gui",
                    localeMap.get(languageChoice1.getValue())));
        });

        languageChoice2.setOnAction(event -> {
            resourceController.setResources(ResourceBundle.getBundle("bundles.gui.gui",
                    localeMap.get(languageChoice1.getValue())));
        });
    }

    public void setLanguages(Map<String, Locale> localeMap){
        localeMap.put("RU", new Locale("ru", "RU"));
        localeMap.put("EN", new Locale("en", "US"));
        localeMap.put("PT", new Locale("pt", "PT"));
        localeMap.put("IT", new Locale("it", "IT"));
    }
}
