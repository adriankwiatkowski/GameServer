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

    public List<GameDto> getAllGames() {
        var games = gameRepository.findAllByOrderByNameAsc();
        return games.stream()
                .map(GameDto::from)
                .collect(Collectors.toList());
    }

    public List<GameDto> getAllGamesByName(String name) {
        var games = gameRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(name);
        return games.stream()
                .map(GameDto::from)
                .collect(Collectors.toList());
    }

    public GameDto getGame(Integer id) {
        var game = gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return GameDto.from(game);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GameDto insert(GameDto gameDto) {
        gameDto.setId(null);
        return upsert(gameDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GameDto update(GameDto gameDto) {
        if (!gameRepository.existsById(gameDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(gameDto);
    }

    private GameDto upsert(GameDto gameDto) {
        var game = GameDto.toGame(gameDto,
                this::getCategories,
                this::getDevelopers,
                this::getGenres,
                this::getPlatforms,
                this::getPublishers);

        gameRepository.save(game);

        return GameDto.from(game);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGame(Integer id) {
        gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        gameRepository.deleteById(id);
    }

    private Set<Category> getCategories(Set<CategoryDto> categories) {
        return categories
                .stream()
                .map(category -> categoryRepository.findById(category.getId()).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Developer> getDevelopers(Set<DeveloperDto> developers) {
        return developers
                .stream()
                .map(developer -> developerRepository.findById(developer.getId()).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Genre> getGenres(Set<GenreDto> genres) {
        return genres
                .stream()
                .map(genre -> genreRepository.findById(genre.getId()).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Platform> getPlatforms(Set<PlatformDto> platforms) {
        return platforms
                .stream()
                .map(platform -> platformRepository.findById(platform.getId()).orElseThrow())
                .collect(Collectors.toSet());
    }

    private Set<Publisher> getPublishers(Set<PublisherDto> publishers) {
        return publishers
                .stream()
                .map(publisher -> publisherRepository.findById(publisher.getId()).orElseThrow())
                .collect(Collectors.toSet());
    }
}
