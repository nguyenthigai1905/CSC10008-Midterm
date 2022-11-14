package com.mmt.client.Controller.Task;

import com.mmt.client.Model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void closeClient(ActionEvent actionEvent) throws Exception {
        // send close signal to server
        ClientModel.getOutput().writeUTF("Close");

        // close client
        ClientModel.getOutput().close();
        ClientModel.getSocket().close();
        System.exit(0);
    }
}
