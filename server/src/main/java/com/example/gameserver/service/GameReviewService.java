package com.example.gameserver.service;

import com.example.gameserver.mapper.GameReviewMapper;
import com.example.gameserver.model.dto.GameReviewDto;
import com.example.gameserver.repository.GameReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameReviewService {

    private final GameReviewRepository gameReviewRepository;
    private final GameReviewMapper gameReviewMapper;

    public GameReviewService(GameReviewRepository gameReviewRepository, GameReviewMapper gameReviewMapper) {
        this.gameReviewRepository = gameReviewRepository;
        this.gameReviewMapper = gameReviewMapper;
    }

    public List<GameReviewDto> getGameReviews(Long gameId) {
        return gameReviewRepository.findAllByGameId(gameId).stream()
                .map(gameReviewMapper::from)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GameReviewDto insert(GameReviewDto gameReviewDto) {
        gameReviewDto.setId(null);
        return upsert(gameReviewDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GameReviewDto update(GameReviewDto gameReviewDto) {
        if (!gameReviewRepository.existsById(gameReviewDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(gameReviewDto);
    }

    private GameReviewDto upsert(GameReviewDto gameReviewDto) {
        var gameReview = gameReviewMapper.toGameReview(gameReviewDto);

        gameReviewRepository.save(gameReview);

        return gameReviewMapper.from(gameReview);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGameReview(Long id) {
        gameReviewRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        gameReviewRepository.deleteById(id);
    }
}
