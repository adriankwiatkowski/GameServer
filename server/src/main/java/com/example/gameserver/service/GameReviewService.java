package com.example.gameserver.service;

import com.example.gameserver.domain.GameReviewEntity;
import com.example.gameserver.dto.GameReviewDto;
import com.example.gameserver.exception.GameReviewDuplicateException;
import com.example.gameserver.mapper.GameReviewMapper;
import com.example.gameserver.repository.GameRepository;
import com.example.gameserver.repository.GameReviewRepository;
import com.example.gameserver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GameReviewService {

    private final GameReviewRepository gameReviewRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameReviewMapper gameReviewMapper;

    public List<GameReviewDto> getGameReviews(Long gameId) {
        ensureGameExists(gameId);

        return gameReviewRepository.findAllByGameId(gameId).stream()
                .map(gameReviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public GameReviewDto insert(String username, GameReviewDto gameReviewDto) {
        gameReviewDto.setId(null);
        return upsert(username, gameReviewDto);
    }

    public GameReviewDto update(String username, GameReviewDto gameReviewDto) {
        ensureGameReviewExists(gameReviewDto.getId());
        return upsert(username, gameReviewDto);
    }

    private GameReviewDto upsert(String username, GameReviewDto gameReviewDto) {
        var gameReview = gameReviewMapper.toEntity(gameReviewDto);
        sanitizeUser(gameReview, username);
        sanitizeGame(gameReview, gameReviewDto.getGameId());

        ensureIsNotDuplicate(gameReview);

        gameReviewRepository.save(gameReview);

        return gameReviewMapper.toDto(gameReview);
    }

    public void deleteGameReview(Long id) {
        ensureGameReviewExists(id);
        gameReviewRepository.deleteById(id);
    }

    private void ensureGameReviewExists(Long id) {
        if (!gameReviewRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("GameReview not found with id: %d", id));
        }
    }

    private void ensureGameExists(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Game not found with id: %d", id));
        }
    }

    private void ensureIsNotDuplicate(GameReviewEntity gameReviewEntity) {
        var isNewEntity = gameReviewEntity.getId() == null;
        if (!isNewEntity) {
            return;
        }

        var userId = gameReviewEntity.getUser().getId();
        var gameId = gameReviewEntity.getGame().getId();

        if (gameReviewRepository.existsByUserIdAndGameId(userId, gameId)) {
            throw new GameReviewDuplicateException(
                    String.format("GameReview already exists for user with id: %d and game with id %d", userId, gameId));
        }
    }

    private void sanitizeUser(GameReviewEntity gameReviewEntity, String username) {
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User not found with username: %s", username)));
        gameReviewEntity.setUser(user);
    }

    private void sanitizeGame(GameReviewEntity gameReviewEntity, Long gameId) {
        var game = gameRepository
                .findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Game not found with id: %d", gameId)));
        gameReviewEntity.setGame(game);
    }
}
