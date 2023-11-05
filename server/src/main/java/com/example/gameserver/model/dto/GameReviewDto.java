package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Game;
import com.example.gameserver.model.domain.GameReview;
import com.example.gameserver.model.domain.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.function.Function;

/**
 * DTO for {@link com.example.gameserver.model.domain.GameReview}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameReviewDto implements Serializable {

    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    @NotNull
    @Size(min = 1, max = 65535)
    @NotBlank
    private String review;

    @NotNull
    private Long userId;

    @NotNull
    private Long gameId;

    public static GameReviewDto from(GameReview gameReview) {
        var gameReviewDto = new GameReviewDto();

        gameReviewDto.setId(gameReview.getId());
        gameReviewDto.setScore(gameReview.getScore());
        gameReviewDto.setReview(gameReview.getReview());
        gameReviewDto.setUserId(gameReview.getUser().getId());
        gameReviewDto.setGameId(gameReview.getGame().getId());

        return gameReviewDto;
    }

    public static GameReview toGameReview(GameReviewDto gameReviewDto,
                                          Function<Long, User> convertUser,
                                          Function<Long, Game> convertGame) {
        var gameReview = new GameReview();

        gameReview.setId(gameReviewDto.getId());
        gameReview.setScore(gameReviewDto.getScore());
        gameReview.setReview(gameReviewDto.getReview());
        gameReview.setUser(convertUser.apply(gameReviewDto.getUserId()));
        gameReview.setGame(convertGame.apply(gameReviewDto.getGameId()));

        return gameReview;
    }
}