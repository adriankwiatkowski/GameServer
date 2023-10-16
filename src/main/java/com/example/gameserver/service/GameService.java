package com.example.gameserver.service;

import com.example.gameserver.model.domain.*;
import com.example.gameserver.model.dto.GameDto;
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
    public Game upsert(GameDto gameDto) {
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
        game.setCategories(getCategoriesByIds(gameDto.categoryIds()));
        game.setDevelopers(getDevelopersByIds(gameDto.developerIds()));
        game.setGenres(getGenresByIds(gameDto.genreIds()));
        game.setPlatforms(getPlatformsByIds(gameDto.platformIds()));
        game.setPublishers(getPublishersByIds(gameDto.publisherIds()));

        gameRepository.save(game);

        return game;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGame(Integer id) {
        gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        gameRepository.deleteById(id);
    }

    private Set<Category> getCategoriesByIds(Set<Integer> ids) {
        return ids
                .stream()
                .map(id -> categoryRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Developer> getDevelopersByIds(Set<Integer> ids) {
        return ids
                .stream()
                .map(id -> developerRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Genre> getGenresByIds(Set<Integer> ids) {
        return ids
                .stream()
                .map(id -> genreRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Platform> getPlatformsByIds(Set<Integer> ids) {
        return ids
                .stream()
                .map(id -> platformRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Publisher> getPublishersByIds(Set<Integer> ids) {
        return ids
                .stream()
                .map(id -> publisherRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
    }
}
