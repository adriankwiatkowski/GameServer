package com.example.client.controller;

import com.example.client.api.ApiServiceGenerator;
import com.example.client.domain.User;
import com.example.client.model.ProfileModel;
import com.example.client.model.UserProperty;
import com.example.client.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable, Controller {
    private UserService userService;
    private ProfileModel profileModel;
    @FXML
    Text usernameText;
    @FXML
    Text nameText;
    @FXML
    Text surnameText;
    @FXML
    Text accessTokenText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void initModel(ScreenController screenController, ProfileModel profileModel) {
        if (this.profileModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.profileModel = profileModel;
        updateUIProfile();

        this.userService = ApiServiceGenerator.createService(UserService.class, this.profileModel.getCurrentToken().getToken());
        Call<User> callAsync = userService.getUserDetails();

        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
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

    }

    public void updateUIProfile() {
        usernameText.setText("Welcome, " + profileModel.getCurrentUser().getUsername());
        nameText.setText("First name: " + profileModel.getCurrentUser().getName());
        surnameText.setText("Last name: " + profileModel.getCurrentUser().getSurname());
        String token = profileModel.getCurrentToken().getToken();
        int tokenSubstr = 9;
        if (token.length() < 9)
            tokenSubstr = token.length();
        accessTokenText.setText("AT: " + token.substring(0, tokenSubstr) + "...");
    }
}
