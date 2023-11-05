package com.example.gameserver.service;

import com.example.gameserver.mapper.GameMapper;
import com.example.gameserver.model.dto.GameDto;
import com.example.gameserver.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public List<GameDto> getAllGames() {
        return gameRepository.findAllByOrderByNameAsc().stream()
                .map(gameMapper::from)
                .collect(Collectors.toList());
    }

    public List<GameDto> getAllGamesByName(String name) {
        return gameRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(name).stream()
                .map(gameMapper::from)
                .collect(Collectors.toList());
    }

    public GameDto getGame(Long id) {
        var game = gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return gameMapper.from(game);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GameDto insert(GameDto gameDto) {
        gameDto.setId(null);
        return upsert(gameDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GameDto update(GameDto gameDto) {
        if (!gameRepository.existsById(gameDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(gameDto);
    }

    private GameDto upsert(GameDto gameDto) {
        var game = gameMapper.toGame(gameDto);

        gameRepository.save(game);

        return gameMapper.from(game);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGame(Long id) {
        gameRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        gameRepository.deleteById(id);
    }
}
