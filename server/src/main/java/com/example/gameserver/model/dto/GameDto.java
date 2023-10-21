package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * DTO for {@link com.example.gameserver.model.domain.Game}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto implements Serializable {

    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(max = 255)
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @PositiveOrZero
    private Integer positiveRatings;

    @NotNull
    @PositiveOrZero
    private Integer negativeRatings;

    @NotNull
    @PositiveOrZero
    private Integer averagePlaytime;

    @NotNull
    @PositiveOrZero
    private Integer medianPlaytime;

    @NotNull
    @Size(max = 255)
    @NotBlank
    @Size(max = 255)
    private String owners;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull(message = "Categories cannot be null")
    private Set<CategoryDto> categories;

    @NotNull
    private Set<DeveloperDto> developers;

    @NotNull
    private Set<GenreDto> genres;

    @NotNull
    private Set<PlatformDto> platforms;

    @NotNull
    private Set<PublisherDto> publishers;

    public static GameDto from(Game game) {
        var gameDto = new GameDto();

        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setDescription(game.getDescription());
        gameDto.setReleaseDate(game.getReleaseDate());
        gameDto.setPositiveRatings(game.getPositiveRatings());
        gameDto.setNegativeRatings(game.getNegativeRatings());
        gameDto.setAveragePlaytime(game.getAveragePlaytime());
        gameDto.setMedianPlaytime(game.getMedianPlaytime());
        gameDto.setOwners(game.getOwners());
        gameDto.setPrice(game.getPrice());
        gameDto.setCategories(game.getCategories()
                .stream()
                .map(CategoryDto::from)
                .collect(Collectors.toSet()));
        gameDto.setDevelopers(game.getDevelopers()
                .stream()
                .map(DeveloperDto::from)
                .collect(Collectors.toSet()));
        gameDto.setGenres(game.getGenres()
                .stream()
                .map(GenreDto::from)
                .collect(Collectors.toSet()));
        gameDto.setPlatforms(game.getPlatforms()
                .stream()
                .map(PlatformDto::from)
                .collect(Collectors.toSet()));
        gameDto.setPublishers(game.getPublishers()
                .stream()
                .map(PublisherDto::from)
                .collect(Collectors.toSet()));

        return gameDto;
    }

    public static Game toGame(GameDto gameDto,
                              Function<Set<CategoryDto>, Set<Category>> convertCategories,
                              Function<Set<DeveloperDto>, Set<Developer>> convertDevelopers,
                              Function<Set<GenreDto>, Set<Genre>> convertGenres,
                              Function<Set<PlatformDto>, Set<Platform>> convertPlatforms,
                              Function<Set<PublisherDto>, Set<Publisher>> convertPublishers) {
        var game = new Game();

        game.setId(gameDto.getId());
        game.setName(gameDto.getName());
        game.setDescription(gameDto.getDescription());
        game.setReleaseDate(gameDto.getReleaseDate());
        game.setPositiveRatings(gameDto.getPositiveRatings());
        game.setNegativeRatings(gameDto.getNegativeRatings());
        game.setAveragePlaytime(gameDto.getAveragePlaytime());
        game.setMedianPlaytime(gameDto.getMedianPlaytime());
        game.setOwners(gameDto.getOwners());
        game.setPrice(gameDto.getPrice());
        game.setCategories(convertCategories.apply(gameDto.getCategories()));
        game.setDevelopers(convertDevelopers.apply(gameDto.getDevelopers()));
        game.setGenres(convertGenres.apply(gameDto.getGenres()));
        game.setPlatforms(convertPlatforms.apply(gameDto.getPlatforms()));
        game.setPublishers(convertPublishers.apply(gameDto.getPublishers()));

        return game;
    }
}