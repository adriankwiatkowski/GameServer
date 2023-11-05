package com.example.gameserver.controller;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.dto.GameReviewDto;
import com.example.gameserver.service.GameReviewService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamereview")
public class GameReviewController {

    private final GameReviewService gameReviewService;

    public GameReviewController(GameReviewService gameReviewService) {
        this.gameReviewService = gameReviewService;
    }

    @GetMapping("/{gameId}")
    public List<GameReviewDto> getGameReviews(@PathVariable Long gameId) {
        return gameReviewService.getGameReviews(gameId);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PostMapping
    public GameReviewDto post(@RequestBody @Valid GameReviewDto gameReviewDto) {
        return gameReviewService.insert(gameReviewDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PutMapping
    public GameReviewDto put(@RequestBody @Valid GameReviewDto gameReviewDto) {
        return gameReviewService.update(gameReviewDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gameReviewService.deleteGameReview(id);
    }
}
