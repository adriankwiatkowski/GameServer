package com.example.client.controller;

import com.example.client.model.ProfileModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Builder;

import java.io.IOException;

@Builder
public class ScreenController {
    private ProfileModel profileModel;
    private FXMLLoader defaultLoader;
    private Stage stage;
    private Scene scene;

    public void addScreen(FXMLLoader loader) throws IOException {
        Pane root = loader.load();
        Controller controller = loader.getController();
        controller.initModel(this, profileModel);
        this.scene.setRoot(root);
    }

    public void run() throws IOException {
        this.scene = new Scene(new Pane(), 850, 500);
        this.stage.setScene(scene);
        this.stage.show();
        this.addScreen(this.defaultLoader);
    }
}
