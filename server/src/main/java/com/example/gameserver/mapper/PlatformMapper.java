package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Platform;
import com.example.gameserver.model.dto.PlatformDto;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {

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
}