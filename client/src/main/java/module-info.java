module com.mmt.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires javafx.swing;

    opens com.mmt.client.Controller to javafx.fxml;
    opens com.mmt.client.Controller.Task to javafx.fxml;
    exports com.mmt.client;
}