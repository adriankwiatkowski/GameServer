package com.example.gameserver.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "game_category",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categories = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "game_developer",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<DeveloperEntity> developers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreEntity> genres = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "game_platform",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<PlatformEntity> platforms = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "game_publisher",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    private Set<PublisherEntity> publishers = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<GameReviewEntity> gameReviews = new HashSet<>();
}