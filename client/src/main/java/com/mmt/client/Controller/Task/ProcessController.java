package com.mmt.client.Controller.Task;

import com.mmt.client.Model.ClientModel;
import com.mmt.client.Model.TableHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProcessController implements Initializable {
    @FXML
    private TableView<String> processTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // client send message to server
        try {
            ClientModel.getOutput().writeUTF("Show Process");
            ClientModel.getOutput().writeUTF("0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // run on javafx thread
        Platform.setImplicitExit(false);
        TableHandler p = new TableHandler(processTable);
        new Thread(p).start();
    }
}
