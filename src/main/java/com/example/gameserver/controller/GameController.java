package com.example.gameserver.controller;

import com.example.gameserver.model.domain.Game;
import com.example.gameserver.model.dto.GameDto;
import com.example.gameserver.service.GameService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/name/{name}")
    public List<Game> getGamesByName(@PathVariable String name) {
        return gameService.getAllGamesByName(name);
    }

    @GetMapping("/{id}")
    public Game get(@PathVariable Integer id) {
        return gameService.getGame(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public Game post(@RequestBody @Valid GameDto gameDto) {
        return gameService.upsert(gameDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping
    public Game put(@RequestBody @Valid GameDto gameDto) {
        return gameService.upsert(gameDto);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        gameService.deleteGame(id);
    }
}
