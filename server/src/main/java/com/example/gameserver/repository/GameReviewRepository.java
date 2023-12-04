package com.example.gameserver.repository;

import com.example.gameserver.domain.GameReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameReviewRepository extends JpaRepository<GameReviewEntity, Long> {

    List<GameReviewEntity> findAllByGameId(Long gameId);

    boolean existsByUserIdAndGameId(Long userId, Long gameId);
}