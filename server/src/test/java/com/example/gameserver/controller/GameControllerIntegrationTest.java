package com.example.gameserver.controller;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.dto.GameDto;
import com.example.gameserver.service.GameService;
import com.example.gameserver.util.Authority;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GameServerApplication.class)
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc
@Transactional
class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameService gameService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @Test
    void givenNotAuthorized_whenGetGames_thenReturns401() throws Exception {
        mockMvc.perform(get("/api/game"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = Authority.USER_SCOPE)
    void givenGames_whenGetGames_thenReturns200() throws Exception {
        givenGameDto(getGameDto("Testowa Gra 1"));
        givenGameDto(getGameDto("Testowa Gra 2"));

        mockMvc.perform(get("/api/game"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Testowa Gra 1")))
                .andExpect(jsonPath("$[1].name", is("Testowa Gra 2")));
    }

    @Test
    @WithMockUser(authorities = Authority.USER_SCOPE)
    void givenGame_whenGetGamesByName_thenReturns200() throws Exception {
        givenGameDto(getGameDto("Testowa Gra"));

        mockMvc.perform(get("/api/game/name/Testowa Gra"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Testowa Gra")));
    }

    @Test
    @WithMockUser(authorities = Authority.USER_SCOPE)
    void givenGame_whenGetGamesByNonExistentName_thenReturnsEmptyList() throws Exception {
        givenGameDto(getGameDto("Testowa Gra"));

        mockMvc.perform(get("/api/game/name/Nowa Gra"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(authorities = Authority.USER_SCOPE)
    void givenGame_whenGetGameById_thenReturns200() throws Exception {
        GameDto gameDto = givenGameDto(getGameDto("Testowa Gra"));
        long gameId = gameDto.getId();

        mockMvc.perform(get("/api/game/" + gameId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Testowa Gra")));
    }

    @Test
    void givenNotAuthorized_whenPostValidGame_thenReturns401() throws Exception {
        GameDto gameDto = getGameDto("Testowa Gra");

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = Authority.USER_SCOPE)
    void givenNotAdmin_whenPostValidGame_thenReturns403() throws Exception {
        GameDto gameDto = getGameDto("Testowa Gra");

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void givenGame_whenPostGame_thenReturns200() throws Exception {
        GameDto gameDto = getGameDto("Testowa Gra");

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Testowa Gra")));
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void givenInvalidGameName_whenPostGame_thenReturns400() throws Exception {
        GameDto gameDto = getGameDto("");

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void givenInvalidGameDescription_whenPostGame_thenReturns400() throws Exception {
        GameDto gameDto = getGameDto("Testowa Gra");
        gameDto.setDescription("");

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void givenGame_whenPutGame_thenReturns200() throws Exception {
        GameDto gameDto = givenGameDto(getGameDto("Testowa Gra"));

        gameDto.setName("Zaktualizowana Gra");

        mockMvc.perform(put("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Zaktualizowana Gra")));
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void givenNonExistentGameId_whenPutGame_thenReturns404() throws Exception {
        GameDto gameDto = getGameDto("Testowa Gra");
        gameDto.setId(1L);

        mockMvc.perform(put("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void givenInvalidGameName_whenPutGame_thenReturns400() throws Exception {
        GameDto gameDto = givenGameDto(getGameDto("Testowa Gra"));
        gameDto.setName("");

        mockMvc.perform(put("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void givenInvalidGameDescription_whenPutGame_thenReturns400() throws Exception {
        GameDto gameDto = givenGameDto(getGameDto("Testowa Gra"));
        gameDto.setDescription("");

        mockMvc.perform(put("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void givenGame_whenDeleteGame_thenReturns200() throws Exception {
        GameDto gameDto = givenGameDto(getGameDto("Testowa Gra"));
        long gameIdToDelete = gameDto.getId();

        mockMvc.perform(delete("/api/game/" + gameIdToDelete))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = Authority.ADMIN_SCOPE)
    void whenDeleteGame_thenReturns404() throws Exception {
        mockMvc.perform(delete("/api/game/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound());
    }

    private GameDto givenGameDto(GameDto gameDto) {
        return gameService.insert(gameDto);
    }

    private GameDto getGameDto(String name) {
        return GameDto.builder()
                .name(name)
                .description("Opis")
                .releaseDate(LocalDate.now())
                .positiveRatings(10)
                .negativeRatings(1)
                .averagePlaytime(100)
                .medianPlaytime(50)
                .owners("10000-50000")
                .price(new BigDecimal("19.99"))
                .categories(new HashSet<>())
                .developers(new HashSet<>())
                .genres(new HashSet<>())
                .platforms(new HashSet<>())
                .publishers(new HashSet<>())
                .build();
    }
}