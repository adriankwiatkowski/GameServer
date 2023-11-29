package com.example.client;

import com.example.client.controller.ScreenController;
import com.example.client.domain.Token;
import com.example.client.domain.User;
import com.example.client.model.ProfileModel;
import com.example.client.model.TokenProperty;
import com.example.client.model.UserProperty;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        Application.setUserAgentStylesheet(getClass().getResource("/css/cupertino-light.css").toExternalForm());

        // Set up loader for first scene
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

        // Prepare persiste & observable data
        ProfileModel profileModel = new ProfileModel();
        profileModel.setCurrentToken(new TokenProperty());
        profileModel.setCurrentUser(new UserProperty());
        //profileModel.setCurrentProfile(new Profile(1l, "Kamil","Bogdanowicz", "Dzik123", "123123123"));
        ScreenController screenController = ScreenController
                .builder()
                .profileModel(profileModel)
                .defaultLoader(loginLoader)
                .stage(stage)
                .build();

        screenController.run();
    }

    public static void main(String[] args) {
        launch();
    }
}