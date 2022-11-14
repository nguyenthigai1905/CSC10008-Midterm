module com.mmt.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires com.github.kwhat.jnativehook;

    opens com.mmt.server.Controller to javafx.fxml;
    exports com.mmt.server;
}