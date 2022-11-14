package com.mmt.client.Controller.Task;

import com.mmt.client.Model.ClientModel;
import com.mmt.client.Model.StageModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScreenshotController implements Initializable {
    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    private BufferedImage imageReceive;

    private WritableImage image;
    @FXML
    private ImageView ssImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void screenShot(ActionEvent actionEvent) throws IOException {
        // send message to server
        ClientModel.getOutput().writeUTF("Screen Shot");
        ClientModel.getOutput().writeUTF("0");
        ClientModel.getOutput().flush();
    }

    @FXML
    private void openScreenshot(ActionEvent actionEvent) throws IOException {
        // read image from server
        do {
            imageReceive = ImageIO.read(ClientModel.getBufferedInput());
        } while (imageReceive == null);

        // show image
        image = SwingFXUtils.toFXImage(imageReceive, null);
        ssImage.setImage(image);
    }

    @FXML
    private void saveScreenshot(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(StageModel.getMainStage());
        if (file != null) {
            ImageIO.write(imageReceive, "PNG", file);
        }
    }
}
