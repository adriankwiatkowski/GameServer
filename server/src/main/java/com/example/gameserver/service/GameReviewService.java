package com.example.gameserver.service;

import com.example.gameserver.dto.GameReviewDto;
import com.example.gameserver.mapper.GameReviewMapper;
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
    private final GameReviewMapper gameReviewMapper;

    public GameReviewService(GameReviewRepository gameReviewRepository,
                             UserRepository userRepository,
                             GameRepository gameRepository,
                             GameReviewMapper gameReviewMapper) {
        this.gameReviewRepository = gameReviewRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameReviewMapper = gameReviewMapper;
    }

    public List<GameReviewDto> getGameReviews(Long gameId) {
        return gameReviewRepository.findAllByGameId(gameId).stream()
                .map(gameReviewMapper::from)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GameReviewDto insert(String username, GameReviewDto gameReviewDto) {
        gameReviewDto.setId(null);
        return upsert(username, gameReviewDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GameReviewDto update(String username, GameReviewDto gameReviewDto) {
        if (!gameReviewRepository.existsById(gameReviewDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(username, gameReviewDto);
    }

    private GameReviewDto upsert(String username, GameReviewDto gameReviewDto) {
        sanitizeUserId(username, gameReviewDto);

        var gameReview = gameReviewMapper.toGameReview(gameReviewDto);
        gameReview.setUser(userRepository.findById(gameReviewDto.getUserId()).orElseThrow(EntityNotFoundException::new));
        gameReview.setGame(gameRepository.findById(gameReviewDto.getGameId()).orElseThrow(EntityNotFoundException::new));

        gameReviewRepository.save(gameReview);

        return gameReviewMapper.from(gameReview);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGameReview(Long id) {
        gameReviewRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        gameReviewRepository.deleteById(id);
    }

    private void sanitizeUserId(String username, GameReviewDto gameReviewDto) {
        var user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        gameReviewDto.setUserId(user.getId());
    }
}
