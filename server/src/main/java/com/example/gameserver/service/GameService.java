package com.example.gameserver.service;

import com.example.gameserver.domain.*;
import com.example.gameserver.dto.*;
import com.example.gameserver.mapper.GameMapper;
import com.example.gameserver.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;
    private final DeveloperRepository developerRepository;
    private final GenreRepository genreRepository;

    private final PlatformRepository platformRepository;
    private final PublisherRepository publisherRepository;

    private final GameMapper gameMapper;

    public List<GameDto> getAllGames() {
        return gameRepository.findAllByOrderByNameAsc().stream()
                .map(gameMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GameDto> getAllGamesByName(String name) {
        return gameRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(name).stream()
                .map(gameMapper::toDto)
                .collect(Collectors.toList());
    }

    public GameDto getGame(Long id) {
        var game = gameRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Game not found with id: %d", id)));
        return gameMapper.toDto(game);
    }

    public GameDto insert(GameDto gameDto) {
        gameDto.setId(null);
        return upsert(gameDto);
    }

    public GameDto update(GameDto gameDto) {
        ensureGameExists(gameDto.getId());
        return upsert(gameDto);
    }

    private GameDto upsert(GameDto gameDto) {
        var game = gameMapper.toEntity(gameDto);
        game.setCategories(getCategoriesByIds(gameDto.getCategories()));
        game.setDevelopers(getDevelopersByIds(gameDto.getDevelopers()));
        game.setGenres(getGenresByIds(gameDto.getGenres()));
        game.setPlatforms(getPlatformsByIds(gameDto.getPlatforms()));
        game.setPublishers(getPublishersByIds(gameDto.getPublishers()));

        gameRepository.save(game);

        return gameMapper.toDto(game);
    }

    public void deleteGame(Long id) {
        ensureGameExists(id);
        gameRepository.deleteById(id);
    }

    private Set<CategoryEntity> getCategoriesByIds(Set<CategoryDto> categoryDtos) {
        var categoryIds = categoryDtos.stream()
                .map(CategoryDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(categoryRepository.findAllById(categoryIds));
    }

    private Set<DeveloperEntity> getDevelopersByIds(Set<DeveloperDto> developerDtos) {
        var developerIds = developerDtos.stream()
                .map(DeveloperDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(developerRepository.findAllById(developerIds));
    }

    private Set<GenreEntity> getGenresByIds(Set<GenreDto> genreDtos) {
        var genreIds = genreDtos.stream()
                .map(GenreDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }

    private Set<PlatformEntity> getPlatformsByIds(Set<PlatformDto> platformDtos) {
        var platformIds = platformDtos.stream()
                .map(PlatformDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(platformRepository.findAllById(platformIds));
    }

    private Set<PublisherEntity> getPublishersByIds(Set<PublisherDto> publisherDtos) {
        var publisherIds = publisherDtos.stream()
                .map(PublisherDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(publisherRepository.findAllById(publisherIds));
    }

    private void ensureGameExists(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Game not found with id: %d", id));
        }
    }
}
