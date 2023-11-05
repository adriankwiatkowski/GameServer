package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.GameReview;
import com.example.gameserver.model.dto.GameReviewDto;
import org.springframework.stereotype.Component;

@Component
public class GameReviewMapper {

    private final UserMapper userMapper;
    private final GameMapper gameMapper;

    public GameReviewMapper(UserMapper userMapper, GameMapper gameMapper) {
        this.userMapper = userMapper;
        this.gameMapper = gameMapper;
    }

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
        gameReview.setUser(userMapper.findById(gameReviewDto.getUserId()));
        gameReview.setGame(gameMapper.findById(gameReviewDto.getGameId()));

        return gameReview;
    }
}