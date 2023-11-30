package com.example.gameserver.mapper;

import com.example.gameserver.domain.GameReview;
import com.example.gameserver.dto.GameReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameReviewMapper {

    private final UserMapper userMapper;

    public GameReviewDto from(GameReview gameReview) {
        return GameReviewDto.builder()
                .id(gameReview.getId())
                .score(gameReview.getScore())
                .review(gameReview.getReview())
                .userDto(userMapper.from(gameReview.getUser()))
                .gameId(gameReview.getGame().getId())
                .build();
    }

    public GameReview toGameReview(GameReviewDto gameReviewDto) {
        return GameReview.builder()
                .id(gameReviewDto.getId())
                .score(gameReviewDto.getScore())
                .review(gameReviewDto.getReview())
                .user(userMapper.toUser(gameReviewDto.getUserDto()))
                .build();
    }
}