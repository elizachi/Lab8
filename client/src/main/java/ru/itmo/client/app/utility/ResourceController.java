package ru.itmo.client.app.utility;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceController {
    private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    /**
     * @return Resources
     */
    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    /**
     * @return Resource bundle
     */
    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    /**
     * Set resources
     * @param resources Resources
     */
    public final void setResources(ResourceBundle resources) {
        resourcesProperty().set(resources);
    }

    /**
     * Binds strings
     * @param key Key for resource
     * @return Binding string
     */
    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }

            @Override
            public String computeValue() {
                return getResources().getString(key);
            }
        };
    }

    public String tryResource(String str, String arg) {
        try {
            if (resources == null) throw new NullPointerException();
            if (arg == null) return getResources().getString(str);
            else {
                MessageFormat messageFormat = new MessageFormat(getResources().getString(str));
                Object[] args = {arg};
                return messageFormat.format(args);
            }
        } catch (MissingResourceException | NullPointerException exception) {
            return str;
        }
    }
}
