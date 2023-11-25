package com.example.gameserver.mapper;

import com.example.gameserver.domain.Game;
import com.example.gameserver.dto.GameDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameMapper {

    private final CategoryMapper categoryMapper;
    private final DeveloperMapper developerMapper;
    private final GenreMapper genreMapper;
    private final PlatformMapper platformMapper;
    private final PublisherMapper publisherMapper;

    public GameMapper(CategoryMapper categoryMapper,
                      DeveloperMapper developerMapper,
                      GenreMapper genreMapper,
                      PlatformMapper platformMapper,
                      PublisherMapper publisherMapper) {
        this.categoryMapper = categoryMapper;
        this.developerMapper = developerMapper;
        this.genreMapper = genreMapper;
        this.platformMapper = platformMapper;
        this.publisherMapper = publisherMapper;
    }

    public GameDto from(Game game) {
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
                .map(categoryMapper::from)
                .collect(Collectors.toSet()));
        gameDto.setDevelopers(game.getDevelopers()
                .stream()
                .map(developerMapper::from)
                .collect(Collectors.toSet()));
        gameDto.setGenres(game.getGenres()
                .stream()
                .map(genreMapper::from)
                .collect(Collectors.toSet()));
        gameDto.setPlatforms(game.getPlatforms()
                .stream()
                .map(platformMapper::from)
                .collect(Collectors.toSet()));
        gameDto.setPublishers(game.getPublishers()
                .stream()
                .map(publisherMapper::from)
                .collect(Collectors.toSet()));

        return gameDto;
    }

    public Game toGame(GameDto gameDto) {
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
        game.setCategories(gameDto.getCategories().stream()
                .map(categoryMapper::toCategory)
                .collect(Collectors.toSet()));
        game.setDevelopers(gameDto.getDevelopers().stream()
                .map(developerMapper::toDeveloper)
                .collect(Collectors.toSet()));
        game.setGenres(gameDto.getGenres().stream()
                .map(genreMapper::toGenre)
                .collect(Collectors.toSet()));
        game.setPlatforms(gameDto.getPlatforms().stream()
                .map(platformMapper::toPlatform)
                .collect(Collectors.toSet()));
        game.setPublishers(gameDto.getPublishers().stream()
                .map(publisherMapper::toPublisher)
                .collect(Collectors.toSet()));

        return game;
    }
}