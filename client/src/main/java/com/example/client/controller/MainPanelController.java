package com.example.client.controller;

import com.example.client.api.ApiServiceGenerator;
import com.example.client.domain.Role;
import com.example.client.domain.User;
import com.example.client.model.ProfileModel;
import com.example.client.model.TokenProperty;
import com.example.client.model.UserProperty;
import com.example.client.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class MainPanelController implements Initializable, Controller {
    private UserService userService;
    private ScreenController screenController;
    private ProfileModel profileModel;
    @FXML
    Text usernameText;
    @FXML
    Text nameText;
    @FXML
    Text surnameText;
    @FXML
    Text accessTokenText;
    @FXML
    Text roleText;
    @FXML
    private HBox adminNavbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminNavbar.managedProperty().bind(adminNavbar.visibleProperty());
        adminNavbar.setVisible(false);
    }

    @Override
    public void initModel(ScreenController screenController, ProfileModel profileModel) {
        if (this.profileModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.profileModel = profileModel;
        this.screenController = screenController;
        updateUIProfile();

        this.userService = ApiServiceGenerator.createService(UserService.class, this.profileModel.getCurrentToken().getToken());
        Call<User> callAsync = userService.getUserDetails();

        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    System.out.println(user);
                    profileModel.setCurrentUser(new UserProperty(user));
                    updateUIProfile();
                } else {
                    System.out.println("Error");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                System.out.println("test");
            }
        });
    }

    @FXML
    public void logout(ActionEvent event) {

        profileModel.setCurrentToken(new TokenProperty());
        profileModel.setCurrentUser(new UserProperty());
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        try {
            screenController.addScreen(lo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void manageGameLink(ActionEvent event) {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/view/manageGames.fxml"));
        try {
            screenController.addScreen(lo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void createGameLink(ActionEvent event) {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/view/createGame.fxml"));
        try {
            screenController.addScreen(lo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUIProfile() {
        Set<Role> setRoles = profileModel.getCurrentUser().getRoles();
        usernameText.setText("Welcome, " + profileModel.getCurrentUser().getUsername());
        nameText.setText("First name: " + profileModel.getCurrentUser().getName());
        surnameText.setText("Last name: " + profileModel.getCurrentUser().getSurname());

        String roles = "";
        for (Role role : setRoles) {
            roles += role.getName();
        }
        roleText.setText("Roles: " + roles);

        String token = profileModel.getCurrentToken().getToken();
        int tokenSubstr = 9;
        if (token.length() < 9)
            tokenSubstr = token.length();
        accessTokenText.setText("AT: " + token.substring(0, tokenSubstr) + "...");

        if (setRoles.stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
            adminNavbar.setVisible(true);
        }
    }

    @FXML
    public void showGameList() {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/view/gameList.fxml"));
        try {
            this.screenController.addScreen(lo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
