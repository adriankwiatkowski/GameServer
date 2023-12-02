package com.example.gameserver.mapper;

import com.example.gameserver.domain.GameReviewEntity;
import com.example.gameserver.dto.GameReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameReviewMapper {

    private final UserMapper userMapper;

    public GameReviewDto from(GameReviewEntity gameReviewEntity) {
        return GameReviewDto.builder()
                .id(gameReviewEntity.getId())
                .score(gameReviewEntity.getScore())
                .review(gameReviewEntity.getReview())
                .userDto(userMapper.from(gameReviewEntity.getUser()))
                .gameId(gameReviewEntity.getGame().getId())
                .build();
    }

    public GameReviewEntity toGameReview(GameReviewDto gameReviewDto) {
        return GameReviewEntity.builder()
                .id(gameReviewDto.getId())
                .score(gameReviewDto.getScore())
                .review(gameReviewDto.getReview())
                .user(userMapper.toUser(gameReviewDto.getUserDto()))
                .build();
    }
}