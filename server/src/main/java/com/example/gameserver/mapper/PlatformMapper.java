package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Platform;
import com.example.gameserver.model.dto.PlatformDto;
import com.example.gameserver.repository.PlatformRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlatformMapper {

    private final PlatformRepository platformRepository;

    public PlatformMapper(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public PlatformDto from(Platform platform) {
        var platformDto = new PlatformDto();

        platformDto.setId(platform.getId());
        platformDto.setName(platform.getName());

        return platformDto;
    }

    public Platform toPlatform(PlatformDto platformDto) {
        var platform = new Platform();

        platform.setId(platformDto.getId());
        platform.setName(platformDto.getName());

        return platform;
    }

    public Set<Platform> getByIds(Set<PlatformDto> platformDtos) {
        var platformIds = platformDtos.stream()
                .map(PlatformDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(platformRepository.findAllById(platformIds));
    }
}