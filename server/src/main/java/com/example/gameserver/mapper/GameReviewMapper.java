package com.example.gameserver.mapper;

import com.example.gameserver.domain.GameReview;
import com.example.gameserver.dto.GameReviewDto;
import org.springframework.stereotype.Component;

@Component
public class GameReviewMapper {

    public GameReviewDto from(GameReview gameReview) {
        return GameReviewDto.builder()
                .id(gameReview.getId())
                .score(gameReview.getScore())
                .review(gameReview.getReview())
                .userId(gameReview.getUser().getId())
                .gameId(gameReview.getGame().getId())
                .build();
    }

    public GameReview toGameReview(GameReviewDto gameReviewDto) {
        return GameReview.builder()
                .id(gameReviewDto.getId())
                .score(gameReviewDto.getScore())
                .review(gameReviewDto.getReview())
                .build();
    }
}