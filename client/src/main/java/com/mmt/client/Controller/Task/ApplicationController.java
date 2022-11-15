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
        handleSend("Show All App", allApp);
    }

    @FXML
    private void openApp(ActionEvent actionEvent) {
        handleSelect("Open App");
    }

    @FXML
    private void closeApp(ActionEvent actionEvent) {
        handleSelect("Close App");
    }

    @FXML
    private void showApp(ActionEvent actionEvent) {
        handleSend("Show Running App", runningApp);
    }

    private void handleSend(String msg, TableView<String> table) {
        try {
            ClientModel.getOutput().writeUTF(msg);
            ClientModel.getOutput().writeUTF("0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.setImplicitExit(false);
        TableHandler p = new TableHandler(table);
        new Thread(p).start();
    }

    private void handleSelect(String msg) {
        String param = null;
        try {
            ClientModel.getOutput().writeUTF(msg);
            String selected = allApp.getSelectionModel().getSelectedItem();
            param = selected.split(",")[1];
            System.out.println(param);
            ClientModel.getOutput().writeUTF(param);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
