module ru.itmo.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires ru.itmo.common;

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
//    exports com.gluonhq.charm.glisten.control;
//    opens com.gluonhq.charm.glisten.control to javafx.fxml;
}
