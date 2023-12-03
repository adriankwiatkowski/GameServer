package com.example.client.controller;

import com.example.client.model.ProfileModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Builder;

import java.io.IOException;
import java.util.HashMap;

@Builder
public class ScreenController {
    @Builder.Default
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private ProfileModel profileModel;
    private FXMLLoader defaultLoader;
    private Stage stage;
    private Scene scene;

    public void addScreen(String name, FXMLLoader loader) throws IOException {
        Pane root = loader.load();
        Controller controller = loader.getController();
        controller.initModel(this, profileModel);
        screenMap.put(name, root);
    }

    public void removeScreen(String name) {
        screenMap.remove(name);
    }

    public void activate(String name) throws IOException {
        if (screenMap.get(name) != null) {
            this.scene.setRoot(screenMap.get(name));
        }
    }

    public void run() throws IOException {
        this.addScreen("default", this.defaultLoader);
        this.scene = new Scene(screenMap.get("default"));
        this.stage.setScene(scene);
        this.stage.show();
    }
}
