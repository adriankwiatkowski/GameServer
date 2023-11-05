package com.example.gameserver.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
}