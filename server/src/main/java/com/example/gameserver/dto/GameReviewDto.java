package com.example.gameserver.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private UserDto userDto;

    @NotNull
    private Long gameId;
}