package com.example.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Game {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer positiveRatings;
    private Integer negativeRatings;
    private Integer averagePlaytime;
    private Integer medianPlaytime;
    private String owners;
    private BigDecimal price;
    private Set<Category> categories;
    private Set<Developer> developers;
    private Set<Genre> genres;
    private Set<Platform> platforms;
    private Set<Publisher> publishers;

    public void addCategory(Category category) {
        if (this.categories == null) {
            this.categories = new HashSet<>();
        }
        this.categories.add(category);
    }

    public void addDeveloper(Developer developer) {
        if (this.developers == null) {
            this.developers = new HashSet<>();
        }
        this.developers.add(developer);
    }

    public void addGenre(Genre genre) {
        if (this.genres == null) {
            this.genres = new HashSet<>();
        }
        this.genres.add(genre);
    }

    public void addPlatform(Platform platform) {
        if (this.platforms == null) {
            this.platforms = new HashSet<>();
        }
        this.platforms.add(platform);
    }

    public void addPublisher(Publisher publisher) {
        if (this.publishers == null) {
            this.publishers = new HashSet<>();
        }
        this.publishers.add(publisher);
    }


    public Game() {
        this.id = 0L; // Assuming default ID is 0
        this.name = ""; // Empty string for name
        this.description = ""; // Empty string for description
        this.releaseDate = LocalDate.now(); // Current date for releaseDate
        this.positiveRatings = 0; // Default value for ratings
        this.negativeRatings = 0; // Default value for ratings
        this.averagePlaytime = 0; // Default value for playtime
        this.medianPlaytime = 0; // Default value for playtime
        this.owners = ""; // Empty string for owners
        this.price = BigDecimal.ZERO; // Default value for price

        this.categories = new HashSet<>();
        this.developers = new HashSet<>();
        this.genres = new HashSet<>();
        this.platforms = new HashSet<>();
        this.publishers = new HashSet<>();
    }
}
