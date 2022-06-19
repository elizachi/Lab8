module ru.itmo.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires ru.itmo.common;
    requires java.desktop;
    requires org.apache.poi.ooxml;

    opens ru.itmo.client to javafx.fxml;
    exports ru.itmo.client;
    exports ru.itmo.client.general;
    opens ru.itmo.client.general to javafx.fxml;
    exports ru.itmo.client.auth.utility;
    opens ru.itmo.client.auth.utility to javafx.fxml;
    exports ru.itmo.client.app.controllers;
    opens ru.itmo.client.app.controllers to javafx.fxml;
    exports ru.itmo.client.auth.controllers;
    opens ru.itmo.client.auth.controllers to javafx.fxml;

}
