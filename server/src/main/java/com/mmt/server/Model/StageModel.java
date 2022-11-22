package com.mmt.server.Model;

import com.mmt.server.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class StageModel {
    // set scene with label for changing scene (use hashmap because it will have key: lable and value: scene)
    private static final HashMap<String, SceneModel> sceneInstance = new HashMap<>();
    // main stage
    private static Stage mainStage;
    // main stage model (apply singleton Pattern)
    private static StageModel mainModel;
    // current scene
    private static SceneModel currentScene;

    // constructor
    private StageModel() {
    }

    // create stage
    public static void init(Stage stage) {
        if (mainModel == null)
            mainModel = new StageModel();

        mainStage = stage;
        mainStage.initStyle(StageStyle.DECORATED);
    }

    // create scene after create stage
    public static void createScene(String label, String fxml, String icon, String title) {
        SceneModel scene = new SceneModel(fxml, title, icon);
        sceneInstance.put(label, scene);
    }

    // create first scene
    public static void switchScene(String label) throws IOException {
        SceneModel firstScene = sceneInstance.get(label);
        loadScene(firstScene);
    }

    // switch to scene by label
    public static void switchScene(String label, Object data) throws IOException {
        // set scene from label
        SceneModel scene = sceneInstance.get(label);
        // send data from another scene to new scene
        if (data != null) scene.data = data;
        // load scene
        loadScene(scene);
    }

    private static void loadScene(SceneModel scene) throws IOException {
        // set current scene to this scene
        currentScene = scene;

        // set stage and show
        Parent root = loadFXML(scene.fxml).load();
        Image icon = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Image/" + scene.icon)));
        Scene s = new Scene(root);
        s.setFill(Color.TRANSPARENT);
        mainStage.setTitle(scene.title);
        mainStage.getIcons().add(icon);
        mainStage.setScene(s);
        mainStage.show();
    }

    private static FXMLLoader loadFXML(String fxml) {
        return new FXMLLoader(Main.class.getResource("/View/" + fxml));
    }

    public static Object getData() {
        return currentScene.data;
    }

    // create SceneModel class
    private static class SceneModel {
        // name of fxml file of scene
        private final String fxml;
        // scene title
        private final String title;
        // scene icon
        private final String icon;
        // scene data (use Object for general type)
        private Object data;

        private SceneModel(String fxml, String title, String icon) {
            this.fxml = fxml;
            this.title = title;
            this.icon = icon;
        }
    }

}
