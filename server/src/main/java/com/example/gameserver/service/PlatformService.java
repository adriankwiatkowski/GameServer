package com.example.gameserver.service;

import com.example.gameserver.dto.PlatformDto;
import com.example.gameserver.mapper.PlatformMapper;
import com.example.gameserver.repository.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository platformRepository;
    private final PlatformMapper platformMapper;

    public List<PlatformDto> getAllPlatforms() {
        return platformRepository.findAll().stream()
                .map(platformMapper::toDto)
                .collect(Collectors.toList());
    }

    public PlatformDto insert(PlatformDto platformDto) {
        platformDto.setId(null);
        return upsert(platformDto);
    }

    public PlatformDto update(PlatformDto platformDto) {
        ensurePlatformExists(platformDto.getId());
        return upsert(platformDto);
    }

    private PlatformDto upsert(PlatformDto platformDto) {
        var platform = platformMapper.toEntity(platformDto);

        platformRepository.save(platform);

        return platformMapper.toDto(platform);
    }

    public void deletePlatform(Long id) {
        ensurePlatformExists(id);
        platformRepository.deleteById(id);
    }

    private void ensurePlatformExists(Long id) {
        if (!platformRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Platform not found with id: %d", id));
        }
    }
}
