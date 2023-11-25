package com.example.gameserver.mapper;

import com.example.gameserver.domain.Game;
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

    public GameDto from(Game game) {
        return GameDto.builder()
                .id(game.getId())
                .name(game.getName())
                .description(game.getDescription())
                .releaseDate(game.getReleaseDate())
                .positiveRatings(game.getPositiveRatings())
                .negativeRatings(game.getNegativeRatings())
                .averagePlaytime(game.getAveragePlaytime())
                .medianPlaytime(game.getMedianPlaytime())
                .owners(game.getOwners())
                .price(game.getPrice())
                .categories(game.getCategories().stream()
                        .map(categoryMapper::from)
                        .collect(Collectors.toSet()))
                .developers(game.getDevelopers().stream()
                        .map(developerMapper::from)
                        .collect(Collectors.toSet()))
                .genres(game.getGenres().stream()
                        .map(genreMapper::from)
                        .collect(Collectors.toSet()))
                .platforms(game.getPlatforms().stream()
                        .map(platformMapper::from)
                        .collect(Collectors.toSet()))
                .publishers(game.getPublishers().stream()
                        .map(publisherMapper::from)
                        .collect(Collectors.toSet()))
                .build();
    }

    public Game toGame(GameDto gameDto) {
        return Game.builder()
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
                        .map(categoryMapper::toCategory)
                        .collect(Collectors.toSet()))
                .developers(gameDto.getDevelopers().stream()
                        .map(developerMapper::toDeveloper)
                        .collect(Collectors.toSet()))
                .genres(gameDto.getGenres().stream()
                        .map(genreMapper::toGenre)
                        .collect(Collectors.toSet()))
                .platforms(gameDto.getPlatforms().stream()
                        .map(platformMapper::toPlatform)
                        .collect(Collectors.toSet()))
                .publishers(gameDto.getPublishers().stream()
                        .map(publisherMapper::toPublisher)
                        .collect(Collectors.toSet()))
                .build();
    }
}