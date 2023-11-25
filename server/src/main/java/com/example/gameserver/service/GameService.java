package com.example.gameserver.service;

import com.example.gameserver.domain.*;
import com.example.gameserver.dto.*;
import com.example.gameserver.mapper.GameMapper;
import com.example.gameserver.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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

    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository,
                       CategoryRepository categoryRepository,
                       DeveloperRepository developerRepository,
                       GenreRepository genreRepository,
                       PlatformRepository platformRepository,
                       PublisherRepository publisherRepository,
                       GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.developerRepository = developerRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
        this.publisherRepository = publisherRepository;
        this.gameMapper = gameMapper;
    }

    public List<GameDto> getAllGames() {
        return gameRepository.findAllByOrderByNameAsc().stream()
                .map(gameMapper::from)
                .collect(Collectors.toList());
    }

    public List<GameDto> getAllGamesByName(String name) {
        return gameRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(name).stream()
                .map(gameMapper::from)
                .collect(Collectors.toList());
    }

    public GameDto getGame(Long id) {
        var game = gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return gameMapper.from(game);
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
        var game = gameMapper.toGame(gameDto);
        game.setCategories(getCategoriesByIds(gameDto.getCategories()));
        game.setDevelopers(getDevelopersByIds(gameDto.getDevelopers()));
        game.setGenres(getGenresByIds(gameDto.getGenres()));
        game.setPlatforms(getPlatformsByIds(gameDto.getPlatforms()));
        game.setPublishers(getPublishersByIds(gameDto.getPublishers()));

        gameRepository.save(game);

        return gameMapper.from(game);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGame(Long id) {
        gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        gameRepository.deleteById(id);
    }

    private Set<Category> getCategoriesByIds(Set<CategoryDto> categoryDtos) {
        var categoryIds = categoryDtos.stream()
                .map(CategoryDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(categoryRepository.findAllById(categoryIds));
    }

    private Set<Developer> getDevelopersByIds(Set<DeveloperDto> developerDtos) {
        var developerIds = developerDtos.stream()
                .map(DeveloperDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(developerRepository.findAllById(developerIds));
    }

    private Set<Genre> getGenresByIds(Set<GenreDto> genreDtos) {
        var genreIds = genreDtos.stream()
                .map(GenreDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }

    private Set<Platform> getPlatformsByIds(Set<PlatformDto> platformDtos) {
        var platformIds = platformDtos.stream()
                .map(PlatformDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(platformRepository.findAllById(platformIds));
    }

    private Set<Publisher> getPublishersByIds(Set<PublisherDto> publisherDtos) {
        var publisherIds = publisherDtos.stream()
                .map(PublisherDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(publisherRepository.findAllById(publisherIds));
    }
}
