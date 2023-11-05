package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Game;
import com.example.gameserver.model.dto.GameDto;
import com.example.gameserver.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameMapper {

    private final GameRepository gameRepository;
    private final CategoryMapper categoryMapper;
    private final DeveloperMapper developerMapper;
    private final GenreMapper genreMapper;
    private final PlatformMapper platformMapper;
    private final PublisherMapper publisherMapper;

    public GameMapper(GameRepository gameRepository,
                      CategoryMapper categoryMapper,
                      DeveloperMapper developerMapper,
                      GenreMapper genreMapper,
                      PlatformMapper platformMapper,
                      PublisherMapper publisherMapper) {
        this.gameRepository = gameRepository;
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
        game.setCategories(categoryMapper.getByIds(gameDto.getCategories()));
        game.setDevelopers(developerMapper.getByIds(gameDto.getDevelopers()));
        game.setGenres(genreMapper.getByIds(gameDto.getGenres()));
        game.setPlatforms(platformMapper.getByIds(gameDto.getPlatforms()));
        game.setPublishers(publisherMapper.getByIds(gameDto.getPublishers()));

        return game;
    }

    public Game findById(Long id) {
        return gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}