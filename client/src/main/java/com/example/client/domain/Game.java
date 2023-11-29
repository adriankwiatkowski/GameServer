package com.example.client.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
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
}
