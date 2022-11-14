package com.mmt.server;

import com.mmt.server.Model.StageModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
        StageModel.init(stage);
        StageModel.createScene("login", "login.fxml", "icon.png", "Server Login");
        StageModel.switchScene("login");
    }
}