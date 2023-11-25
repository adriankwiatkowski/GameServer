package com.example.gameserver.controller;

import com.example.gameserver.dto.GameDto;
import com.example.gameserver.service.GameService;
import com.example.gameserver.util.Authority;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public List<GameDto> getGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/name/{name}")
    public List<GameDto> getGamesByName(@PathVariable String name) {
        return gameService.getAllGamesByName(name);
    }

    @GetMapping("/{id}")
    public GameDto get(@PathVariable Long id) {
        return gameService.getGame(id);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PostMapping
    public GameDto insert(@RequestBody @Valid GameDto gameDto) {
        return gameService.insert(gameDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PutMapping
    public GameDto update(@RequestBody @Valid GameDto gameDto) {
        return gameService.update(gameDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
