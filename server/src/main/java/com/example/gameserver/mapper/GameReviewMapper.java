package com.example.gameserver.mapper;

import com.example.gameserver.domain.GameReview;
import com.example.gameserver.dto.GameReviewDto;
import org.springframework.stereotype.Component;

@Component
public class GameReviewMapper {

    public GameReviewDto from(GameReview gameReview) {
        var gameReviewDto = new GameReviewDto();

        gameReviewDto.setId(gameReview.getId());
        gameReviewDto.setScore(gameReview.getScore());
        gameReviewDto.setReview(gameReview.getReview());
        gameReviewDto.setUserId(gameReview.getUser().getId());
        gameReviewDto.setGameId(gameReview.getGame().getId());

        return gameReviewDto;
    }

    public GameReview toGameReview(GameReviewDto gameReviewDto) {
        var gameReview = new GameReview();

        gameReview.setId(gameReviewDto.getId());
        gameReview.setScore(gameReviewDto.getScore());
        gameReview.setReview(gameReviewDto.getReview());

        return gameReview;
    }
}