package com.mmt.server.Controller;

import com.mmt.server.Model.ServerModel;
import com.mmt.server.Model.StageModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField tfPort;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void pressEnter(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            // create port from text-field
            String portText = tfPort.getText();
            int port = Integer.parseInt(portText);

            // create server
            ServerModel.create(port);

            // create server info
            List<String> serverInfo = ServerModel.getInfo();

            // create scene
            StageModel.createScene("info", "info.fxml", "icon.png", "Server Info");
            StageModel.switchScene("info", serverInfo);
        }
    }
}
