package com.example.client.model;

import com.example.client.domain.*;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class GameProperty {
    public final ObservableValue<Long> id;
    public final SimpleStringProperty name;
    public final SimpleStringProperty description;
    public final SimpleStringProperty releaseDate;
    public final SimpleIntegerProperty positiveRatings;
    public final SimpleIntegerProperty negativeRatings;
    public final SimpleIntegerProperty averagePlaytime;
    public final SimpleIntegerProperty medianPlaytime;
    public final SimpleStringProperty owners;
    public final SimpleLongProperty price;
    public final SimpleSetProperty<Category> categories;
    public final SimpleSetProperty<Developer> developers;
    public final SimpleSetProperty<Genre> genres;
    public final SimpleSetProperty<Platform> platforms;
    private final SimpleSetProperty<Publisher> publishers;

    public GameProperty(Game game) {
        this.id = new SimpleObjectProperty<>(game.getId());
        this.name = new SimpleStringProperty(game.getName());
        this.description = new SimpleStringProperty(game.getDescription());
        this.releaseDate = new SimpleStringProperty(game.getReleaseDate().toString());
        this.positiveRatings = new SimpleIntegerProperty(game.getPositiveRatings());
        this.negativeRatings = new SimpleIntegerProperty(game.getNegativeRatings());
        this.averagePlaytime = new SimpleIntegerProperty(game.getAveragePlaytime());
        this.medianPlaytime = new SimpleIntegerProperty(game.getMedianPlaytime());
        this.owners = new SimpleStringProperty(game.getOwners());
        this.price = new SimpleLongProperty(game.getPrice().longValue());

        ObservableSet<Category> categoryObservableSet = FXCollections.observableSet(game.getCategories());
        this.categories = new SimpleSetProperty<>(categoryObservableSet);
        ObservableSet<Developer> developersObservableSet = FXCollections.observableSet(game.getDevelopers());
        this.developers = new SimpleSetProperty<>(developersObservableSet);
        ObservableSet<Genre> genresObservableSet = FXCollections.observableSet(game.getGenres());
        this.genres = new SimpleSetProperty<>(genresObservableSet);
        ObservableSet<Platform> platformsObservableSet = FXCollections.observableSet(game.getPlatforms());
        this.platforms = new SimpleSetProperty<>(platformsObservableSet);
        ObservableSet<Publisher> publishersObservableSet = FXCollections.observableSet(game.getPublishers());
        this.publishers = new SimpleSetProperty<>(publishersObservableSet);
    }
}
