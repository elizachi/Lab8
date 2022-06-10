module ru.itmo.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires ru.itmo.common;

    opens ru.itmo.client to javafx.fxml;
    exports ru.itmo.client;
    exports ru.itmo.client.controllers;
    opens ru.itmo.client.controllers to javafx.fxml;
}
