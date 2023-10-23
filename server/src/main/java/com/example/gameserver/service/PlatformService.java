package com.example.gameserver.service;

import com.example.gameserver.model.dto.PlatformDto;
import com.example.gameserver.repository.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;

    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public List<PlatformDto> getAllPlatforms() {
        return platformRepository.findAll().stream()
                .map(PlatformDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PlatformDto insert(PlatformDto platformDto) {
        platformDto.setId(null);
        return upsert(platformDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PlatformDto update(PlatformDto platformDto) {
        if (!platformRepository.existsById(platformDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(platformDto);
    }

    private PlatformDto upsert(PlatformDto platformDto) {
        var platform = PlatformDto.toPlatform(platformDto);

        platformRepository.save(platform);

        return PlatformDto.from(platform);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deletePlatform(Integer id) {
        platformRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        platformRepository.deleteById(id);
    }
}
