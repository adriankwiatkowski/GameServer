package com.example.gameserver.controller;

import com.example.gameserver.dto.GameReviewDto;
import com.example.gameserver.service.GameReviewService;
import com.example.gameserver.util.Authority;
import com.example.gameserver.util.OpenApiUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamereview")
@SecurityRequirement(name = OpenApiUtil.SCHEME_NAME)
@RequiredArgsConstructor
public class GameReviewController {

    private final GameReviewService gameReviewService;

    @GetMapping("/{gameId}")
    public List<GameReviewDto> getGameReviews(@PathVariable Long gameId) {
        return gameReviewService.getGameReviews(gameId);
    }

    @PostMapping
    public GameReviewDto insert(@RequestBody @Valid GameReviewDto gameReviewDto) {
        return gameReviewService.insert(getCurrentUsername(), gameReviewDto);
    }

    @PutMapping
    public GameReviewDto update(@RequestBody @Valid GameReviewDto gameReviewDto) {
        return gameReviewService.update(getCurrentUsername(), gameReviewDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gameReviewService.deleteGameReview(id);
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
