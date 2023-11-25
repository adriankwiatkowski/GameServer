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
                .map(platformMapper::from)
                .collect(Collectors.toList());
    }

    public PlatformDto insert(PlatformDto platformDto) {
        platformDto.setId(null);
        return upsert(platformDto);
    }

    public PlatformDto update(PlatformDto platformDto) {
        if (!platformRepository.existsById(platformDto.getId())) {
            throw new EntityNotFoundException(String.format("Platform not found with id: %d", platformDto.getId()));
        }

        return upsert(platformDto);
    }

    private PlatformDto upsert(PlatformDto platformDto) {
        var platform = platformMapper.toPlatform(platformDto);

        platformRepository.save(platform);

        return platformMapper.from(platform);
    }

    public void deletePlatform(Long id) {
        platformRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Platform not found with id: %d", id)));
        platformRepository.deleteById(id);
    }
}
