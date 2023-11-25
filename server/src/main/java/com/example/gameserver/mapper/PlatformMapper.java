package com.example.gameserver.mapper;

import com.example.gameserver.domain.Platform;
import com.example.gameserver.dto.PlatformDto;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {

    public PlatformDto from(Platform platform) {
        return PlatformDto.builder()
                .id(platform.getId())
                .name(platform.getName())
                .build();
    }

    public Platform toPlatform(PlatformDto platformDto) {
        return Platform.builder()
                .id(platformDto.getId())
                .name(platformDto.getName())
                .build();
    }
}