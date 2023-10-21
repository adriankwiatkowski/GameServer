package com.example.gameserver.controller;

import com.example.gameserver.model.Authority;
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
    public List<GameDto> getGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/name/{name}")
    public List<GameDto> getGamesByName(@PathVariable String name) {
        return gameService.getAllGamesByName(name);
    }

    @GetMapping("/{id}")
    public GameDto get(@PathVariable Integer id) {
        return gameService.getGame(id);
    }

    @PreAuthorize(Authority.SCOPE_ADMIN)
    @PostMapping
    public GameDto post(@RequestBody @Valid GameDto gameDto) {
        return gameService.insert(gameDto);
    }

    @PreAuthorize(Authority.SCOPE_ADMIN)
    @PutMapping
    public GameDto put(@RequestBody @Valid GameDto gameDto) {
        return gameService.update(gameDto);
    }

    @PreAuthorize(Authority.SCOPE_ADMIN)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        gameService.deleteGame(id);
    }
}
