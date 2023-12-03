package com.example.client.controller;

import com.example.client.api.ApiServiceGenerator;
import com.example.client.domain.Token;
import com.example.client.dto.RegisterDto;
import com.example.client.model.ProfileModel;
import com.example.client.model.TokenProperty;
import com.example.client.service.AuthService;
import com.example.client.utils.CommonChecks;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.synedra.validatorfx.Validator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.client.utils.ErrorHandler.ErrorHandler;

public class RegisterController implements Initializable, Controller {
    private ScreenController screenController;
    private ProfileModel profileModel;
    private Validator validator = new Validator();
    @FXML
    private PasswordField confPasswordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;
    @FXML
    private Text errorText;

    @FXML
    void loginLink(ActionEvent event) {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        try {
            screenController.addScreen(lo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void register(ActionEvent event) {
        if(validator.containsErrors()) {
            errorText.setText("Check all fields");
            return;
        }
        String username = userNameField.getText();
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        String password = passwordField.getText();
        RegisterDto registerDto = new RegisterDto(firstname, lastname, username, password);

        AuthService authService = ApiServiceGenerator.createService(AuthService.class);
        Call<ResponseBody> callAsync = authService.registerUser(registerDto);
        callAsync.enqueue(registerCallback());
    }

    @Override
    public void initModel(ScreenController screenController, ProfileModel profileModel) {
        if (this.profileModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.profileModel = profileModel;
        this.screenController = screenController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validate();
    }

    private void validate() {
        validator.createCheck().dependsOn("username", userNameField.textProperty()).withMethod(c -> {
            CommonChecks.required(c, "username");
        }).decorates(userNameField).immediate();
        ;
        validator.createCheck().dependsOn("firstname", firstNameField.textProperty()).withMethod(c -> {
            CommonChecks.required(c, "firstname");
        }).decorates(firstNameField).immediate();
        ;
        validator.createCheck().dependsOn("lastname", lastNameField.textProperty()).withMethod(c -> {
            CommonChecks.required(c, "lastname");
        }).decorates(lastNameField).immediate();
        ;
        validator.createCheck().dependsOn("password", passwordField.textProperty()).withMethod(c -> {
            CommonChecks.required(c, "password");
        }).decorates(passwordField).immediate();
        ;
        validator.createCheck().dependsOn("confpassword", confPasswordField.textProperty()).withMethod(c -> {
            String password = passwordField.getText();
            String confirmPassword = confPasswordField.getText();

            if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
                c.error("Passwords do not match");
            }
        }).decorates(confPasswordField).immediate();
        ;
    }

    private Callback<ResponseBody> registerCallback() {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
                    errorText.setText("Konto zostalo utworzone");
                } else {
                    errorText.setText(ErrorHandler(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                errorText.setText("Something went wrong ... Try again");
            }
        };
    }
}
