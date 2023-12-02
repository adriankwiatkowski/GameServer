package com.example.client.controller;

import com.example.client.controller.components.GameTableController;
import com.example.client.model.GameProperty;
import com.example.client.model.ProfileModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameListController implements Controller, Initializable {
    private GameTableController gameTableController;
    private ProfileModel profileModel;
    private ScreenController screenController;
    @FXML
    private Pagination pagination;
    @FXML
    private VBox gameInfo;
    private List<GameProperty> gamePropertyList = new ArrayList<>();

    @Override
    public void initModel(ScreenController screenController, ProfileModel profileModel) {
        if (this.profileModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.profileModel = profileModel;
        this.gameTableController.updateProfileModel(profileModel);
        this.screenController = screenController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameInfo.getChildren().add(new Text("23"));

        gameTableController = GameTableController.builder().profileModel(this.profileModel).rowPerPage(10).pagination(this.pagination).gamePropertyList(this.gamePropertyList).gameInfo(this.gameInfo).build();
        gameTableController.createTable();
        gameTableController.init();
    }

    @FXML
    private void backToMenu() {
        try {
            this.screenController.activate("mainPanel");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
