package com.example.gameserver.service;

import com.example.gameserver.model.domain.Game;
import com.example.gameserver.model.domain.User;
import com.example.gameserver.model.dto.GameReviewDto;
import com.example.gameserver.repository.GameRepository;
import com.example.gameserver.repository.GameReviewRepository;
import com.example.gameserver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameReviewService {

    private final GameReviewRepository gameReviewRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public GameReviewService(GameReviewRepository gameReviewRepository,
                             UserRepository userRepository,
                             GameRepository gameRepository) {
        this.gameReviewRepository = gameReviewRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public List<GameReviewDto> getGameReviews(Long gameId) {
        return gameReviewRepository.findAllByGameId(gameId).stream()
                .map(GameReviewDto::from)
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
        var gameReview = GameReviewDto.toGameReview(gameReviewDto, this::getUser, this::getGame);

        gameReviewRepository.save(gameReview);

        return GameReviewDto.from(gameReview);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGameReview(Long id) {
        gameReviewRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        gameReviewRepository.deleteById(id);
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private Game getGame(Long id) {
        return gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
