package com.example.gameserver.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
        name = "game_review",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "game_id"})}
)
public class GameReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull
    @Lob
    @Column(name = "review", length = 65535, nullable = false)
    private String review;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;
}