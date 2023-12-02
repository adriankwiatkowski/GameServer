package com.example.gameserver.service;

import com.example.gameserver.dto.GameReviewDto;
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
        return gameReviewRepository.findAllByGameId(gameId).stream()
                .map(gameReviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public GameReviewDto insert(String username, GameReviewDto gameReviewDto) {
        gameReviewDto.setId(null);
        return upsert(username, gameReviewDto);
    }

    public GameReviewDto update(String username, GameReviewDto gameReviewDto) {
        if (!gameReviewRepository.existsById(gameReviewDto.getId())) {
            throw new EntityNotFoundException(String.format("GameReview not found with id: %d", gameReviewDto.getId()));
        }

        return upsert(username, gameReviewDto);
    }

    private GameReviewDto upsert(String username, GameReviewDto gameReviewDto) {
        sanitizeUserId(username, gameReviewDto);

        var gameReview = gameReviewMapper.toEntity(gameReviewDto);
        gameReview.setUser(userRepository
                .findById(gameReviewDto.getUserDto().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User not found with id: %d", gameReviewDto.getUserDto().getId()))));
        gameReview.setGame(gameRepository
                .findById(gameReviewDto.getGameId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Game not found with id: %d", gameReviewDto.getGameId()))));

        gameReviewRepository.save(gameReview);

        return gameReviewMapper.toDto(gameReview);
    }

    public void deleteGameReview(Long id) {
        gameReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("GameReview not found with id: %d", id)));
        gameReviewRepository.deleteById(id);
    }

    private void sanitizeUserId(String username, GameReviewDto gameReviewDto) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User not found with username: %s", username)));
        gameReviewDto.getUserDto().setId(user.getId());
    }
}
