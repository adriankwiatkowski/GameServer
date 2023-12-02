package com.example.client.controller;

import com.example.client.api.ApiServiceGenerator;
import com.example.client.domain.Token;
import com.example.client.dto.LoginDto;
import com.example.client.model.ProfileModel;
import com.example.client.model.TokenProperty;
import com.example.client.service.AuthService;
import com.example.client.utils.CommonChecks;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.synedra.validatorfx.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.client.utils.ErrorHandler.ErrorHandler;

public class LoginController implements Initializable, Controller {
    private ProfileModel profileModel;
    private ScreenController screenController;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Button loginButton;
    @FXML
    Text errorText;
    private Validator validator = new Validator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validate();
    }

    public void initModel(ScreenController screenController, ProfileModel profileModel) {
        if (this.profileModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.profileModel = profileModel;
        this.screenController = screenController;
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        if(validator.containsErrors()) {
            errorText.setText("Check all fields");
            return;
        }
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        LoginDto loginDto = new LoginDto(username, password);

        AuthService authService = ApiServiceGenerator.createService(AuthService.class);
        Call<Token> callAsync = authService.loginUser(loginDto);
        callAsync.enqueue(loginCallback());
    }

    @FXML
    private void registerLink(ActionEvent event) {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/view/register.fxml"));
        try {
            screenController.addScreen(lo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void validate() {
        validator.createCheck().dependsOn("username", usernameTextField.textProperty()).withMethod(c -> {
            CommonChecks.required(c, "username");
        }).decorates(usernameTextField).immediate();
        ;
        validator.createCheck().dependsOn("password", passwordTextField.textProperty()).withMethod(c -> {
            CommonChecks.required(c, "password");
        }).decorates(passwordTextField).immediate();
        ;
    }

    private Callback<Token> loginCallback() {
        return new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token token = response.body();
                    profileModel.setCurrentToken(new TokenProperty(token));

                    FXMLLoader lo = new FXMLLoader(getClass().getResource("/view/mainPanel.fxml"));
                    try {
                        screenController.addScreen(lo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    errorText.setText(ErrorHandler(response));
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                errorText.setText("Something went wrong ... Try again");
            }
        };
    }
}