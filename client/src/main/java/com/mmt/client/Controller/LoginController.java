package com.mmt.client.Controller;

import com.mmt.client.Model.ClientModel;
import com.mmt.client.Model.StageModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private final Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    private final Alert error = new Alert(Alert.AlertType.ERROR);
    @FXML
    private TextField textName;
    @FXML
    private TextField textPort;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // create ok popup
        confirm.setTitle("Chấp thuận");
        confirm.setContentText("Đã kết nối thành công với server");
        // create error popup
        error.setTitle("Không thể kết nối");
        error.setContentText("Có thể server đã đóng hoặc bị lỗi");
    }

    @FXML
    private void clickLogin(ActionEvent actionEvent) {
        String name = textName.getText();
        Integer port = Integer.parseInt(textPort.getText());

        try {
            ClientModel.create(name, port);
        } catch (Exception e) {
            error.show();
            System.exit(0);
        }

        try {
            StageModel.createScene("Main task", "task.fxml", "icon.png", "Remote Task");
            StageModel.switchScene("Main task");
            confirm.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
