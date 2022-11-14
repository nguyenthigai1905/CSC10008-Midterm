package com.mmt.client.Controller.Task;

import com.mmt.client.Model.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KeylistenController implements Initializable {
    @FXML
    private TextArea writeHere;
    private String built = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void Start(ActionEvent actionEvent) throws IOException {
        ClientModel.getOutput().writeUTF("Key Listener");
        ClientModel.getOutput().writeUTF("0");
        ClientModel.getOutput().flush();

        // nhan tu server
        new Thread(() -> {
            String s = null;
            writeHere.setText("Bắt đầu bắt phím:");
            while (true) {
                try {
                    s = ClientModel.getInput().readUTF();
                    built = built + s + " ";
                    writeHere.setText(built);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(s);
            }
        }).start();


    }

    @FXML
    public void End(ActionEvent actionEvent) throws IOException {
        ClientModel.getOutput().writeUTF("Stop Key Listener");
        ClientModel.getOutput().writeUTF("0");
        ClientModel.getOutput().flush();
        writeHere.setText("Dừng bắt phím");
        built = "";
    }
}