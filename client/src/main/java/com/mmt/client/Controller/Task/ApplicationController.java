package com.mmt.client.Controller.Task;

import com.mmt.client.Model.ClientModel;
import com.mmt.client.Model.TableHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    @FXML
    private TableView<String> allApp;

    @FXML
    private TableView<String> runningApp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ClientModel.getOutput().writeUTF("Show All App");
            ClientModel.getOutput().writeUTF("0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.setImplicitExit(false);
        TableHandler p = new TableHandler(allApp);
        Platform.runLater(p);
    }

    @FXML
    private void openApp(ActionEvent actionEvent) {

    }

    @FXML
    private void closeApp(ActionEvent actionEvent) {

    }

    @FXML
    private void showApp(ActionEvent actionEvent) {
        try {
            ClientModel.getOutput().writeUTF("Show Running App");
            ClientModel.getOutput().writeUTF("0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.setImplicitExit(false);
        TableHandler p = new TableHandler(runningApp);
        new Thread(p).start();
    }
}
