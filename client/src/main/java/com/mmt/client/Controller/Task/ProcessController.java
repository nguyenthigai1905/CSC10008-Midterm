package com.mmt.client.Controller.Task;

import com.mmt.client.Model.ClientModel;
import com.mmt.client.Model.TableHandler;
import javafx.event.ActionEvent;
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
        TableHandler p = new TableHandler(processTable);
        new Thread(p).start();
    }

    @FXML
    private void endProcess(ActionEvent actionEvent) throws IOException {
        String param = null;
        try {
            ClientModel.getOutput().writeUTF("End Process");

            String selected = processTable.getSelectionModel().getSelectedItem();
            param = selected.split(",")[1];
            System.out.println(param);

            ClientModel.getOutput().writeUTF(param);

            processTable.getItems().removeAll(processTable.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
