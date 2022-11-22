package com.mmt.server.Controller;

import com.mmt.server.Model.ServerHandlerModel;
import com.mmt.server.Model.ServerModel;
import com.mmt.server.Model.StageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InfoController implements Initializable {
    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final List<String> serverInfo = takeData();
    private final ClipboardContent content = new ClipboardContent();
    @FXML
    private Label labelPort;
    @FXML
    private Label labelIP;
    @FXML
    private Label labelStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelIP.setText("Server Private IP: " + serverInfo.get(0));
        labelPort.setText("Server Port: " + serverInfo.get(1));
        labelStatus.setText("Server Status: " + "Not Connected");
    }

    @FXML
    private void copyIP(ActionEvent actionEvent) {
        content.putString(serverInfo.get(0));
        clipboard.setContent(content);
    }

    @FXML
    private void copyPort(ActionEvent actionEvent) {
        content.putString(serverInfo.get(1));
        clipboard.setContent(content);
    }

    @FXML
    private void openServer(ActionEvent actionEvent) {
        if (!ServerModel.isConnect()) {
            // connect to socket and set status
            ServerModel.connect();
            labelStatus.setText("Server Status: " + "Connected");

            // create server handler and run it in thread
            ServerHandlerModel.create();
            new Thread(ServerHandlerModel.getModel()).start();
        }
    }

    private List<String> takeData() {
        String data = String.valueOf(StageModel.getData()).replaceAll("[\\[\\]]", "");
        return Arrays.asList(data.split(", "));
    }
}
