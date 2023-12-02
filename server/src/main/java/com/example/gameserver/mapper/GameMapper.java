package com.example.gameserver.mapper;

import com.example.gameserver.domain.GameEntity;
import com.example.gameserver.dto.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final CategoryMapper categoryMapper;
    private final DeveloperMapper developerMapper;
    private final GenreMapper genreMapper;
    private final PlatformMapper platformMapper;
    private final PublisherMapper publisherMapper;

    public GameDto toDto(GameEntity gameEntity) {
        return GameDto.builder()
                .id(gameEntity.getId())
                .name(gameEntity.getName())
                .description(gameEntity.getDescription())
                .releaseDate(gameEntity.getReleaseDate())
                .positiveRatings(gameEntity.getPositiveRatings())
                .negativeRatings(gameEntity.getNegativeRatings())
                .averagePlaytime(gameEntity.getAveragePlaytime())
                .medianPlaytime(gameEntity.getMedianPlaytime())
                .owners(gameEntity.getOwners())
                .price(gameEntity.getPrice())
                .categories(gameEntity.getCategories().stream()
                        .map(categoryMapper::toDto)
                        .collect(Collectors.toSet()))
                .developers(gameEntity.getDevelopers().stream()
                        .map(developerMapper::toDto)
                        .collect(Collectors.toSet()))
                .genres(gameEntity.getGenres().stream()
                        .map(genreMapper::toDto)
                        .collect(Collectors.toSet()))
                .platforms(gameEntity.getPlatforms().stream()
                        .map(platformMapper::toDto)
                        .collect(Collectors.toSet()))
                .publishers(gameEntity.getPublishers().stream()
                        .map(publisherMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public GameEntity toEntity(GameDto gameDto) {
        return GameEntity.builder()
                .id(gameDto.getId())
                .name(gameDto.getName())
                .description(gameDto.getDescription())
                .releaseDate(gameDto.getReleaseDate())
                .positiveRatings(gameDto.getPositiveRatings())
                .negativeRatings(gameDto.getNegativeRatings())
                .averagePlaytime(gameDto.getAveragePlaytime())
                .medianPlaytime(gameDto.getMedianPlaytime())
                .owners(gameDto.getOwners())
                .price(gameDto.getPrice())
                .categories(gameDto.getCategories().stream()
                        .map(categoryMapper::toEntity)
                        .collect(Collectors.toSet()))
                .developers(gameDto.getDevelopers().stream()
                        .map(developerMapper::toEntity)
                        .collect(Collectors.toSet()))
                .genres(gameDto.getGenres().stream()
                        .map(genreMapper::toEntity)
                        .collect(Collectors.toSet()))
                .platforms(gameDto.getPlatforms().stream()
                        .map(platformMapper::toEntity)
                        .collect(Collectors.toSet()))
                .publishers(gameDto.getPublishers().stream()
                        .map(publisherMapper::toEntity)
                        .collect(Collectors.toSet()))
                .build();
    }
}