package com.mmt.client.Controller;

import com.mmt.client.Main;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TaskController implements Initializable {

    @FXML
    private Pane Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane client;

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Exit.setOnMouseClicked(event -> System.exit(0));
        client.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(client);

            slide.setToX(0);
            slide.play();

            client.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(client);

            slide.setToX(-176);
            slide.play();

            client.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
        try {
            openFXML("dashboard.fxml");
        } catch (IOException e) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void dashboardOpen(ActionEvent actionEvent) throws IOException {
        openFXML("dashboard.fxml");
    }

    @FXML
    private void shutdownOpen(ActionEvent actionEvent) throws IOException {
        openFXML("shutdown.fxml");
    }

    @FXML
    private void screenshotOpen(ActionEvent actionEvent) throws IOException {
        openFXML("screenshot.fxml");
    }

    @FXML
    private void processesOpen(ActionEvent actionEvent) throws IOException {
        openFXML("process.fxml");
    }

    @FXML
    private void applicationOpen(ActionEvent actionEvent) throws IOException {
        openFXML("application.fxml");
    }

    @FXML
    private void keyListenerOpen(ActionEvent actionEvent) throws IOException {
        openFXML("keylisten.fxml");
    }

    private void openFXML(String fxmlFile) throws IOException {
        Parent fxml = FXMLLoader.load(Main.class.getResource("/View/Task/" + fxmlFile));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

}
