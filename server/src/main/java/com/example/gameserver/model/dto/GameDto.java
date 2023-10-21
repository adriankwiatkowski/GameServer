package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true, fluent = true)
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

        gameDto.id(game.getId())
                .name(game.getName())
                .description(game.getDescription())
                .releaseDate(game.getReleaseDate())
                .positiveRatings(game.getPositiveRatings())
                .negativeRatings(game.getNegativeRatings())
                .averagePlaytime(game.getAveragePlaytime())
                .medianPlaytime(game.getMedianPlaytime())
                .owners(game.getOwners())
                .price(game.getPrice())
                .categories(
                        game.getCategories()
                                .stream()
                                .map(CategoryDto::from)
                                .collect(Collectors.toSet()))
                .developers(
                        game.getDevelopers()
                                .stream()
                                .map(DeveloperDto::from)
                                .collect(Collectors.toSet()))
                .genres(
                        game.getGenres()
                                .stream()
                                .map(GenreDto::from)
                                .collect(Collectors.toSet()))
                .platforms(
                        game.getPlatforms()
                                .stream()
                                .map(PlatformDto::from)
                                .collect(Collectors.toSet()))
                .publishers(
                        game.getPublishers()
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

        game.setId(gameDto.id());
        game.setName(gameDto.name());
        game.setDescription(gameDto.description());
        game.setReleaseDate(gameDto.releaseDate());
        game.setPositiveRatings(gameDto.positiveRatings());
        game.setNegativeRatings(gameDto.negativeRatings());
        game.setAveragePlaytime(gameDto.averagePlaytime());
        game.setMedianPlaytime(gameDto.medianPlaytime());
        game.setOwners(gameDto.owners());
        game.setPrice(gameDto.price());
        game.setCategories(convertCategories.apply(gameDto.categories()));
        game.setDevelopers(convertDevelopers.apply(gameDto.developers()));
        game.setGenres(convertGenres.apply(gameDto.genres()));
        game.setPlatforms(convertPlatforms.apply(gameDto.platforms()));
        game.setPublishers(convertPublishers.apply(gameDto.publishers()));

        return game;
    }
}