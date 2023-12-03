package com.example.client.controller;

import com.example.client.api.ApiServiceGenerator;
import com.example.client.domain.*;
import com.example.client.model.ProfileModel;
import com.example.client.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.client.utils.FieldSetter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import static com.example.client.utils.ErrorHandler.ErrorHandler;

public class UpdateGameController implements Controller, Initializable {
    private ProfileModel profileModel;
    private ScreenController screenController;
    private Game game;
    @FXML
    private TextField avgPlaytimeField;

    @FXML
    private ComboBox<Category> categoriesField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<Developer> developersField;

    @FXML
    private Text errorText;

    @FXML
    private ComboBox<Genre> genresField;

    @FXML
    private TextField medianPlaytimeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField negativeRateField;

    @FXML
    private TextField owners;

    @FXML
    private ComboBox<Platform> platformsField;

    @FXML
    private TextField positiveRateField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Publisher> publishersField;

    @FXML
    private DatePicker releaseDateField;

    @FXML
    void updateGame(ActionEvent event) {
        System.out.println(game);
        GameService gameService = ApiServiceGenerator.createService(GameService.class, this.profileModel.getCurrentToken().getToken());
        Call<Game> callCreateGame = gameService.updateGame(game);
        callCreateGame.enqueue(updateGameCallback());
    }
    @Override
    public void initModel(ScreenController screenController, ProfileModel profileModel) {
        if (this.profileModel != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.profileModel = profileModel;
        this.screenController = screenController;

        // Platform
        PlatformService platformService = ApiServiceGenerator.createService(PlatformService.class);
        Call<List<Platform>> callPlatforms = platformService.getAllPlatforms();
        populateComboBox(callPlatforms, platformsField, createStringConverter(Platform::getName));

// Developer
        DeveloperService developerService = ApiServiceGenerator.createService(DeveloperService.class);
        Call<List<Developer>> callDevelopers = developerService.getAllDevelopers();
        populateComboBox(callDevelopers, developersField, createStringConverter(Developer::getName));

// Genre
        GenreService genreService = ApiServiceGenerator.createService(GenreService.class);
        Call<List<Genre>> callGenres = genreService.getAllGenres();
        populateComboBox(callGenres, genresField, createStringConverter(Genre::getName));

// Publisher
        PublisherService publisherService = ApiServiceGenerator.createService(PublisherService.class);
        Call<List<Publisher>> callPublishers = publisherService.getAllPublishers();
        populateComboBox(callPublishers, publishersField, createStringConverter(Publisher::getName));

// Categories
        CategoryService categoriesService = ApiServiceGenerator.createService(CategoryService.class);
        Call<List<Category>> callCategories = categoriesService.getAllCategories();
        populateComboBox(callCategories, categoriesField, createStringConverter(Category::getName));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.game = new Game();
        addListenerToTextField(nameField, game::setName);
        addListenerToTextField(descriptionField, game::setDescription);
        addListenerToNumberField(positiveRateField, game::setPositiveRatings);
        addListenerToNumberField(negativeRateField, game::setNegativeRatings);
        addListenerToNumberField(avgPlaytimeField, game::setAveragePlaytime);
        addListenerToNumberField(medianPlaytimeField, game::setMedianPlaytime);
        releaseDateField.valueProperty().addListener((observable, oldValue, newValue) -> {
            game.setReleaseDate(newValue);
        });
        addListenerToTextField(owners, game::setOwners);
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                newValue.replaceAll("[^\\d]", "");
                priceField.setText(newValue);
            }
            if (game != null) {
                game.setPrice(new BigDecimal(newValue));
            }
        });
        addListenerToComboBox(categoriesField, game::addCategory);
        addListenerToComboBox(developersField, game::addDeveloper);
        addListenerToComboBox(genresField, game::addGenre);
        addListenerToComboBox(platformsField, game::addPlatform);
        addListenerToComboBox(publishersField, game::addPublisher);
    }

    private <T> void populateComboBox(Call<List<T>> call, ComboBox<T> comboBox, StringConverter<T> converter) {
        call.enqueue(new Callback<List<T>>() {
            @Override
            public void onResponse(Call<List<T>> call, Response<List<T>> response) {
                List<T> items = response.body();
                ObservableList<T> observableItems = FXCollections.observableArrayList(items);

                javafx.application.Platform.runLater(() -> {
                    comboBox.setConverter(converter);
                    comboBox.setItems(observableItems);
                });
            }

            @Override
            public void onFailure(Call<List<T>> call, Throwable throwable) {
                // Handle failure if needed
            }
        });
    }

    private <T> StringConverter<T> createStringConverter(Function<T, String> toStringFunction) {
        return new StringConverter<T>() {
            @Override
            public String toString(T object) {
                //System.out.println(object.toString());
                return (object != null) ? toStringFunction.apply(object) : "";
            }

            @Override
            public T fromString(String string) {
                return null;
            }
        };
    }

    private <T> void addListenerToTextField(TextField textField, FieldSetter<T> setter) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (game != null) {
                setter.set((T) newValue); // Update the Game object with new value
            }
        });
    }

    private <T> void addListenerToComboBox(ComboBox<?> comboBox, FieldSetter<T> setter) {
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (game != null) {
                setter.set((T) newValue); // Update the Game object with new value
            }
        });
    }

    private void addListenerToNumberField(TextField textField, FieldSetter<Integer> setter) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                newValue.replaceAll("[^\\d]", "");
                textField.setText(newValue);
            }
            if (game != null) {
                setter.set(Integer.parseInt(newValue)); // Update the Game object with new value
            }
        });
    }

    private Callback<Game> updateGameCallback() {
        return new Callback<Game>() {

            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.isSuccessful()) {
                    Game game = response.body();
                    System.out.println(game);
                    errorText.setText("Game updated succesfully");
                } else {
                    System.out.println(response.body());
                    System.out.println(response.raw());
                    errorText.setText(ErrorHandler(response));
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable throwable) {
                errorText.setText("Something went wrong ... Try again");
            }
        };
    }
}
