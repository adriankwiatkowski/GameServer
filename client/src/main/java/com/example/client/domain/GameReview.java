package com.example.client.domain;

import lombok.Data;

@Data
public class GameReview {
    private Long id;
    private Integer score;
    private String review;
    private Long userId;
    private Long gameId;
}
