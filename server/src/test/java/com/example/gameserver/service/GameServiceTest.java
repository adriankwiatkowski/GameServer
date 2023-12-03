package com.example.gameserver.service;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.GameEntity;
import com.example.gameserver.dto.GameDto;
import com.example.gameserver.mapper.GameMapper;
import com.example.gameserver.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GameServerApplication.class)
class GameServiceTest {

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private GameMapper gameMapper;

    @Autowired
    private GameService gameService;

    @Test
    void givenGame_whenInsert_thenOk() {
        GameDto gameDto = GameDto.builder()
                .id(1L)
                .name("Testowa Gra")
                .description("Testowy Opis")
                .releaseDate(LocalDate.now())
                .positiveRatings(100)
                .negativeRatings(10)
                .averagePlaytime(60)
                .medianPlaytime(30)
                .owners("1000-5000")
                .price(new BigDecimal("19.99"))
                .categories(new HashSet<>())
                .developers(new HashSet<>())
                .genres(new HashSet<>())
                .platforms(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        GameEntity game = GameEntity.builder()
                .id(1L)
                .name("Testowa Gra")
                .description("Testowy Opis")
                .releaseDate(LocalDate.now())
                .positiveRatings(100)
                .negativeRatings(10)
                .averagePlaytime(60)
                .medianPlaytime(30)
                .owners("1000-5000")
                .price(new BigDecimal("19.99"))
                .categories(new HashSet<>())
                .developers(new HashSet<>())
                .genres(new HashSet<>())
                .platforms(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        when(gameMapper.toEntity(any(GameDto.class))).thenReturn(game);
        when(gameRepository.save(any(GameEntity.class))).thenReturn(game);
        when(gameMapper.toDto(any(GameEntity.class))).thenReturn(gameDto);

        GameDto result = gameService.insert(gameDto);

        assertNotNull(result);
        assertEquals(gameDto.getId(), result.getId());
        assertEquals(gameDto.getName(), result.getName());
        assertEquals(gameDto.getDescription(), result.getDescription());
        assertEquals(gameDto.getReleaseDate(), result.getReleaseDate());
        assertEquals(gameDto.getPositiveRatings(), result.getPositiveRatings());
        assertEquals(gameDto.getNegativeRatings(), result.getNegativeRatings());
        assertEquals(gameDto.getAveragePlaytime(), result.getAveragePlaytime());
        assertEquals(gameDto.getMedianPlaytime(), result.getMedianPlaytime());
        assertEquals(gameDto.getOwners(), result.getOwners());
        assertEquals(gameDto.getPrice(), result.getPrice());
    }
}
