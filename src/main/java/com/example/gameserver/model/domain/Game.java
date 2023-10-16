package com.example.gameserver.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Lob
    @Column(name = "description", length = 65535, nullable = false)
    private String description;

    @NotNull
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @NotNull
    @Column(name = "positive_ratings", nullable = false)
    private Integer positiveRatings;

    @NotNull
    @Column(name = "negative_ratings", nullable = false)
    private Integer negativeRatings;

    @NotNull
    @Column(name = "average_playtime", nullable = false)
    private Integer averagePlaytime;

    @NotNull
    @Column(name = "median_playtime", nullable = false)
    private Integer medianPlaytime;

    @Size(max = 255)
    @NotNull
    @Column(name = "owners", nullable = false)
    private String owners;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToMany(mappedBy = "games")
    private Set<Category> categories = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "games")
    private Set<Developer> developers = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "games")
    private Set<Genre> genres = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "games")
    private Set<Platform> platforms = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "games")
    private Set<Publisher> publishers = new LinkedHashSet<>();

}