package com.example.gameserver.repository;

import com.example.gameserver.domain.GameReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameReviewRepository extends JpaRepository<GameReview, Long> {

    List<GameReview> findAllByGameId(Long gameId);
}