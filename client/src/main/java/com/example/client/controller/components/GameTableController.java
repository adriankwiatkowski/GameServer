package com.example.client.controller.components;

import com.example.client.api.ApiServiceGenerator;
import com.example.client.controller.ScreenController;
import com.example.client.domain.*;
import com.example.client.model.GameProperty;
import com.example.client.model.ProfileModel;
import com.example.client.service.GameService;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Builder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

@Builder
public class GameTableController {
    private ScreenController screenController;
    private ProfileModel profileModel;
    private Pagination pagination;
    private int rowPerPage;
    private VBox gameInfo;
    private TableView<GameProperty> table;

    public void updateData(ProfileModel profileModel, ScreenController screenController) {
        this.profileModel = profileModel;
        this.screenController = screenController;
        query();
    }

    private List<GameProperty> gamePropertyList;

    public void createTable() {
        TableView<GameProperty> table = new TableView<>();

        table.getColumns().add(createStringColumn("name"));
        table.getColumns().add(createStringColumn("releaseDate"));
        table.getColumns().add(createLongColumn("price"));
        table.getColumns().add(createIntegerColumn("positiveRatings"));

        this.table = table;
    }


    public void init() {
        pagination.setPageFactory(this::createPage);

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background = new Background(backgroundFill);
        gameInfo.setBackground(background);
        table.getSelectionModel().selectedItemProperty().addListener(this::gameInfoListener);
    }

    public void refreshPagination() {
        pagination.setPageFactory(this::createPage);
        table.refresh();
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowPerPage;
        int toIndex = Math.min(fromIndex + rowPerPage, gamePropertyList.size());
        table.setItems(FXCollections.observableArrayList(gamePropertyList.subList(fromIndex, toIndex)));
        return table;
    }

    private TableColumn<GameProperty, String> createStringColumn(String property) {
        TableColumn<GameProperty, String> column = new TableColumn<>(property);
        column.setCellValueFactory(param -> {
            try {
                // Assuming param.getValue() returns a GameProperty instance
                GameProperty gameProperty = param.getValue();
                Field field = GameProperty.class.getDeclaredField(property);
                field.setAccessible(true);
                return (SimpleStringProperty) field.get(gameProperty);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        column.setPrefWidth(100);
        return column;
    }

    private TableColumn<GameProperty, Integer> createIntegerColumn(String property) {
        TableColumn<GameProperty, Integer> column = new TableColumn<>(property);
        column.setCellValueFactory(param -> {
            try {
                GameProperty gameProperty = param.getValue();
                Field field = GameProperty.class.getDeclaredField(property);
                field.setAccessible(true);
                Integer value = ((SimpleIntegerProperty) field.get(gameProperty)).get();
                IntegerProperty observableValue = new SimpleIntegerProperty(value);

                return observableValue.asObject();
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        column.setPrefWidth(100);
        return column;
    }

    private TableColumn<GameProperty, Long> createLongColumn(String property) {
        TableColumn<GameProperty, Long> column = new TableColumn<>(property);
        column.setCellValueFactory(param -> {
            try {
                GameProperty gameProperty = param.getValue();
                Field field = GameProperty.class.getDeclaredField(property);
                field.setAccessible(true);
                Long value = ((SimpleLongProperty) field.get(gameProperty)).get();
                LongProperty observableValue = new SimpleLongProperty(value);

                return observableValue.asObject();
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        column.setPrefWidth(100);
        return column;
    }


    private void query() {
        GameService gameService = ApiServiceGenerator.createService(GameService.class, this.profileModel.getCurrentToken().getToken());
        Call<List<Game>> callAsync = gameService.getAllGames();
        callAsync.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    List<Game> gameList = response.body();
                    if (gameList != null) {
                        for (Game game : gameList) {
                            gamePropertyList.add(new GameProperty(game));
                        }
                    }
                    Platform.runLater(() -> refreshPagination());
                } else {
                    System.out.println("Error");
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable throwable) {

            }
        });
    }

    private void gameInfoListener(ObservableValue<? extends GameProperty> observable, GameProperty oldSelection, GameProperty newSelection) {
        if (newSelection != null) {
            gameInfo.getChildren().clear();

            Set<Role> setRoles = profileModel.getCurrentUser().getRoles();
            if (setRoles.stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
                HBox actionAdmin = new HBox();

                Button removeGameButton = new Button("Delete");
                removeGameButton.setOnAction(event -> handleRemoveGame(event, newSelection));
                actionAdmin.getChildren().addAll(removeGameButton);

                Button updateGameButton = new Button("Update");
                updateGameButton.setOnAction(event -> handleUpdateGame(event, newSelection));
                actionAdmin.getChildren().addAll(updateGameButton);

                gameInfo.getChildren().add(actionAdmin);
            }

            gameInfo.getChildren().add(new Text("Name: " + newSelection.name.get()));
            Text tempText = new Text("Description: " + newSelection.description.get());
            StackPane stackPane = new StackPane();
            stackPane.setMaxWidth(200);// Set the maximum width for the StackPane
            tempText.wrappingWidthProperty().bind(stackPane.widthProperty()); // Enable text wrapping within the StackPane's width
            stackPane.getChildren().add(tempText);

            gameInfo.getChildren().add(stackPane);
            gameInfo.getChildren().add(new Text("Release Date: " + newSelection.releaseDate.get()));
            gameInfo.getChildren().add(new Separator(Orientation.HORIZONTAL));
            gameInfo.getChildren().add(new Text("Positive Ratings: " + newSelection.positiveRatings.get()));
            gameInfo.getChildren().add(new Text("Negative Ratings: " + newSelection.negativeRatings.get()));
            gameInfo.getChildren().add(new Text("AveragePlaytime: " + newSelection.averagePlaytime.get()));
            gameInfo.getChildren().add(new Text("Price: " + newSelection.price.get()));
            gameInfo.getChildren().add(new Text("Developers: "));
            for (Developer developer : newSelection.developers.get()) {
                gameInfo.getChildren().add(new Text(developer.getName()));
            }
            gameInfo.getChildren().add(new Text("Categories: "));
            for (Category category : newSelection.categories.get()) {
                gameInfo.getChildren().add(new Text(category.getName()));
            }
            gameInfo.getChildren().add(new Text("Genres: "));
            for (Genre genre : newSelection.genres.get()) {
                gameInfo.getChildren().add(new Text(genre.getName().toString()));
            }
            gameInfo.getChildren().add(new Text("Platforms: "));
            for (com.example.client.domain.Platform platform : newSelection.platforms.get()) {
                gameInfo.getChildren().add(new Text(platform.getName()));
            }
        }
    }

    private Callback<Void> removeGameCallback() {
        return new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("removed");
                } else {
                    System.out.println("error while removing");
                    //errorText.setText(ErrorHandler(response));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                //errorText.setText("Something went wrong ... Try again");
            }
        };
    }

    private void handleRemoveGame(ActionEvent actionEvent, GameProperty newSelection) {
        GameService gameService = ApiServiceGenerator.createService(GameService.class);
        Call<Void> callAsync = gameService.removeGame(newSelection.id.getValue());
        callAsync.enqueue(removeGameCallback());
    }

    private void handleUpdateGame(ActionEvent actionEvent, GameProperty newSelection) {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("/view/updateGame.fxml"));
        try {
            this.screenController.addScreenWitGameData(lo, newSelection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
