package com.mmt.client.Controller.Task;

import com.mmt.client.Model.ClientModel;
import com.mmt.client.Model.TableHandlerModel;
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
        try {
            ClientModel.getOutput().writeUTF("Open App");

            String selected = allApp.getSelectionModel().getSelectedItem();

            String appPath = selected.split(",")[1].replaceAll("\\\\", "/");
            String param = appPath.substring(1, appPath.length() - 1);

            if (param.startsWith("{")) {
                while (param.contains("/")) {
                    int idx = param.indexOf("/");
                    param = param.substring(idx + 1);
                }
            }

            System.out.println(param);
            ClientModel.getOutput().writeUTF(param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeApp(ActionEvent actionEvent) {
        String param = null;
        try {
            ClientModel.getOutput().writeUTF("Close App");

            String selected = runningApp.getSelectionModel().getSelectedItem();
            param = selected.split(",")[0];
            System.out.println(param);

            ClientModel.getOutput().writeUTF(param);
            runningApp.getItems().removeAll(runningApp.getSelectionModel().getSelectedItem());

        } catch (IOException e) {
            e.printStackTrace();
        }
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

        TableHandlerModel p = new TableHandlerModel(table);
        new Thread(p).start();
    }
}
