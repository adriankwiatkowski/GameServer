package com.example.gameserver.controller;

import com.example.gameserver.dto.GameDto;
import com.example.gameserver.service.GameService;
import com.example.gameserver.util.Authority;
import com.example.gameserver.util.OpenApiUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@SecurityRequirement(name = OpenApiUtil.SCHEME_NAME)
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

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @PostMapping
    public GameDto insert(@RequestBody @Valid GameDto gameDto) {
        return gameService.insert(gameDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @PutMapping
    public GameDto update(@RequestBody @Valid GameDto gameDto) {
        return gameService.update(gameDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
