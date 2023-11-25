package com.example.gameserver.service;

import com.example.gameserver.dto.PlatformDto;
import com.example.gameserver.mapper.PlatformMapper;
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
    private final PlatformMapper platformMapper;

    public PlatformService(PlatformRepository platformRepository, PlatformMapper platformMapper) {
        this.platformRepository = platformRepository;
        this.platformMapper = platformMapper;
    }

    public List<PlatformDto> getAllPlatforms() {
        return platformRepository.findAll().stream()
                .map(platformMapper::from)
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
        var platform = platformMapper.toPlatform(platformDto);

        platformRepository.save(platform);

        return platformMapper.from(platform);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deletePlatform(Long id) {
        platformRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        platformRepository.deleteById(id);
    }
}
