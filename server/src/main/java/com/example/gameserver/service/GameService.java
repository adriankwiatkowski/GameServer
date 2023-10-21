package com.example.gameserver.service;

import com.example.gameserver.model.domain.*;
import com.example.gameserver.model.dto.*;
import com.example.gameserver.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;
    private final DeveloperRepository developerRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final PublisherRepository publisherRepository;

    public GameService(GameRepository gameRepository,
                       CategoryRepository categoryRepository,
                       DeveloperRepository developerRepository,
                       GenreRepository genreRepository,
                       PlatformRepository platformRepository,
                       PublisherRepository publisherRepository) {
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.developerRepository = developerRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
        this.publisherRepository = publisherRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAllByOrderByNameAsc();
    }

    public List<Game> getAllGamesByName(String name) {
        return gameRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(name);
    }

    public Game getGame(Integer id) {
        return gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Game insert(GameDto gameDto) {
        gameDto.id(null);
        return upsert(gameDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Game update(GameDto gameDto) {
        return upsert(gameDto);
    }

    private Game upsert(GameDto gameDto) {
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
        game.setCategories(getCategories(gameDto.categories()));
        game.setDevelopers(getDevelopers(gameDto.developers()));
        game.setGenres(getGenres(gameDto.genres()));
        game.setPlatforms(getPlatforms(gameDto.platforms()));
        game.setPublishers(getPublishers(gameDto.publishers()));

        gameRepository.save(game);

        return game;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGame(Integer id) {
        gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        gameRepository.deleteById(id);
    }

    private Set<Category> getCategories(Set<CategoryDto> categories) {
        return categories
                .stream()
                .map(category -> categoryRepository.findById(category.id()).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Developer> getDevelopers(Set<DeveloperDto> developers) {
        return developers
                .stream()
                .map(developer -> developerRepository.findById(developer.id()).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Genre> getGenres(Set<GenreDto> genres) {
        return genres
                .stream()
                .map(genre -> genreRepository.findById(genre.id()).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Platform> getPlatforms(Set<PlatformDto> platforms) {
        return platforms
                .stream()
                .map(platform -> platformRepository.findById(platform.id()).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Publisher> getPublishers(Set<PublisherDto> publishers) {
        return publishers
                .stream()
                .map(publisher -> publisherRepository.findById(publisher.id()).orElseThrow())
                .collect(Collectors.toSet());
    }
}
